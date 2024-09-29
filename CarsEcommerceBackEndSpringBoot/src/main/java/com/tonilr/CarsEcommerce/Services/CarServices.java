package com.tonilr.CarsEcommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.CarRepo;

@Service
public class CarServices {

	@Autowired
	private final CarRepo carRepo;

	public CarServices(CarRepo carRepo) {
		this.carRepo = carRepo;
	}

	public Car addCar(Car car) {
		return carRepo.save(car);
	}

	public List<Car> findAllCars() {
		return carRepo.findAll();
	}

	public Car updateCar(Car car) {
		return carRepo.save(car);
	}

	public Car findCarById(Long id) {
		return carRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Car by id " + id + " was not found"));

	}

	public void deleteCar(Long id) {
		carRepo.deleteById(id);
	}
}
