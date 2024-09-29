package com.tonilr.CarsEcommerce.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Services.CarServices;

@Controller
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/car")
public class CarController {

	@Autowired
	private final CarServices carService;
	
	public CarController(CarServices carService) {
		this.carService = carService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Car>> getAllCars() {
		List<Car> cars = carService.findAllCars();
		return new ResponseEntity<>(cars, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable("id") Long id) {
		Car car = carService.findCarById(id);
		return new ResponseEntity<>(car, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Car> addCar(@RequestBody Car car) {
		Car newCar = carService.addCar(car);
		return new ResponseEntity<>(newCar, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Car> updateCar(@RequestBody Car car) {
		Car updateCar = carService.updateCar(car);
		return new ResponseEntity<>(updateCar, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
		carService.deleteCar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
