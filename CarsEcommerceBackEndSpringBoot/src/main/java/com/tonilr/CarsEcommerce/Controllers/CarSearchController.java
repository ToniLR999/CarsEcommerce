package com.tonilr.CarsEcommerce.Controllers;

import com.tonilr.CarsEcommerce.DTOs.CarSearchCriteria;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Services.CarSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars/search")
@CrossOrigin(origins = "*")
public class CarSearchController {

    @Autowired
    private CarSearchService carSearchService;

    @PostMapping
    public ResponseEntity<Page<Car>> searchCars(
            @RequestBody CarSearchCriteria criteria,
            Pageable pageable) {
        return ResponseEntity.ok(carSearchService.searchCars(criteria, pageable));
    }
} 