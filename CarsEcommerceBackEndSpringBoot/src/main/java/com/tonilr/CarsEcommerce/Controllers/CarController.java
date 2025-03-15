package com.tonilr.CarsEcommerce.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.tonilr.CarsEcommerce.DTOs.CarDTO;
import com.tonilr.CarsEcommerce.DTOs.UserDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Mappers.CarMapper;
import com.tonilr.CarsEcommerce.Mappers.UserMapper;
import com.tonilr.CarsEcommerce.Services.CarServices;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/car")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@Autowired
	private final CarServices carService;
	
	public CarController(CarServices carService) {
		this.carService = carService;
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
		Car car = carService.findCarById(id);
		
	    CarDTO carDTO = CarMapper.toCarDTO(car);
	    
	    // Retornamos el UserDTO envuelto en un ResponseEntity con código de estado 200 (OK)
	    return ResponseEntity.ok(carDTO);	
	}

	@PostMapping("/add")
	public ResponseEntity<Car> addCar(@RequestBody CarDTO carDTO) {
		
		logger.warn("Iniciando tarea...");

		logger.warn("CarOrders : "+carDTO.getOrders());
		Car newCar = carService.addCar(carDTO);
		return new ResponseEntity<>(newCar, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Car> updateCar(@RequestBody CarDTO carDTO) {
		Car updateCar = carService.updateCar(carDTO);
		return new ResponseEntity<>(updateCar, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
		carService.deleteCar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
