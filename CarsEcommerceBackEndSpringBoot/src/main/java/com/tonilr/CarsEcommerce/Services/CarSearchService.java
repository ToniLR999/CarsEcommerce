package com.tonilr.CarsEcommerce.Services;

import com.tonilr.CarsEcommerce.DTOs.CarSearchCriteria;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Repos.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarSearchService {

    @Autowired
    private CarRepository carRepository;

    @Cacheable(value = "carSearch", key = "#criteria.toString() + '-' + #pageable.toString()")
    public Page<Car> searchCars(CarSearchCriteria criteria, Pageable pageable) {
        return carRepository.findBySearchCriteria(
            criteria.getMarca(),
            criteria.getModelo(),
            criteria.getPrecioMin(),
            criteria.getPrecioMax(),
            criteria.getAñoMin(),
            criteria.getAñoMax(),
            criteria.getCategoria(),
            criteria.getCombustible(),
            criteria.getTransmision(),
            criteria.getKilometrajeMax(),
            criteria.getDisponible(),
            pageable
        );
    }
} 