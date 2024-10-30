package com.tonilr.CarsEcommerce.Controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tonilr.CarsEcommerce.Entities.CarCategory;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/category")
public class CategoryController {
	
    @GetMapping("/all")
    public List<String> getAllCategories() {
        return Arrays.asList(CarCategory.values())  // Devuelve una lista de categorías
            .stream()
            .map(Enum::name)
            .collect(Collectors.toList());
        }
}
