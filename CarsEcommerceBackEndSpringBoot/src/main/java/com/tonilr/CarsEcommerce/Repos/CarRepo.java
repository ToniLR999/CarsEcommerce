package com.tonilr.CarsEcommerce.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tonilr.CarsEcommerce.Entities.Car;

@Repository
public interface CarRepo extends JpaRepository<Car,Long>{

}
