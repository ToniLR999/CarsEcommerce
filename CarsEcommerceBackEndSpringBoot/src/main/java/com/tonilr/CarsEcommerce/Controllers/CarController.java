package com.tonilr.CarsEcommerce.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tonilr.CarsEcommerce.DTOs.CarDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Mappers.CarMapper;
import com.tonilr.CarsEcommerce.Services.CarServices;
import com.tonilr.CarsEcommerce.Services.CacheService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/car")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final CarServices carService;
    private final CacheService cacheService;

    public CarController(CarServices carService, CacheService cacheService) {
        this.carService = carService;
        this.cacheService = cacheService;
    }

	@GetMapping("/all")
	public ResponseEntity<List<CarDTO>> getAllCars() {
		List<Car> cars = carService.findAllCars();
		
	    List<CarDTO> carsDTO = CarMapper.toCarDTO(cars);
	    
	    // Retornamos el UserDTO envuelto en un ResponseEntity con código de estado 200 (OK)
		return new ResponseEntity<>(carsDTO, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<CarDTO> getCarById(@PathVariable("id") Long id) {
		Car car = (Car) cacheService.getProduct(id);
		if (car == null) {
			car = carService.findCarById(id);
			cacheService.saveProduct(id, car);
		}
		CarDTO carDTO = CarMapper.toCarDTO(car);
		
	    // Retornamos el UserDTO envuelto en un ResponseEntity con código de estado 200 (OK)
	    return ResponseEntity.ok(carDTO);	
	}

	@PostMapping("/add")
	public ResponseEntity<Car> addCar(@RequestBody CarDTO carDTO) {
		
		logger.warn("Iniciando tarea...");

		logger.warn("CarOrders : "+carDTO.getOrders());
		Car newCar = carService.addCar(carDTO);
		cacheService.saveProduct(newCar.getId(), newCar);
		return new ResponseEntity<>(newCar, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Car> updateCar(@RequestBody CarDTO carDTO) {
		Car updateCar = carService.updateCar(carDTO);
		cacheService.saveProduct(updateCar.getId(), updateCar);
		return new ResponseEntity<>(updateCar, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
		carService.deleteCar(id);
		cacheService.removeProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/uploadImage/{carId}")
	public ResponseEntity<String> uploadCarImage(@PathVariable Long carId, @RequestParam("file") MultipartFile file) {
	    try {
	        Car car = carService.findCarById(carId);
	        if (car == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coche no encontrado");
	        }

	        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
	        Path path = Paths.get("src/main/resources/CarImages/" + fileName);
	        Files.createDirectories(path.getParent());
	        Files.write(path, file.getBytes());
	        
	        // Guardar solo el nombre del archivo, sin la ruta
	        car.addImage(fileName);
	        
	        carService.saveCar(car);
	        cacheService.saveProduct(carId, car);

	        return ResponseEntity.ok("Imagen subida correctamente");
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
	    }
	}
	
    @DeleteMapping("/deleteImage/{carId}")
    public ResponseEntity<String> deleteCarImage(@PathVariable Long carId, @RequestParam("imageUrl") String imageUrl) {
        try {
            Car car = carService.findCarById(carId);
            if (car == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coche no encontrado");
            }

            String fileName = imageUrl.replace("/images/", "");
            Path path = Paths.get("src/main/resources/static/images/" + fileName);

            if (Files.exists(path)) {
                Files.delete(path);
            }

            car.removeImage(imageUrl);
            carService.saveCar(car); // 🔥 Aquí se guardan los cambios
            cacheService.saveProduct(carId, car);

            return ResponseEntity.ok("Imagen eliminada correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la imagen");
        }

    }
    
}

