package com.tonilr.CarsEcommerce.Controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tonilr.CarsEcommerce.Entities.OrderStatus;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/orderStatus")
public class OrderStatusController {
	
    @GetMapping("/all")
    public List<String> getAllOrderStatuses() {
        return Arrays.asList(OrderStatus.values())  // Devuelve una lista de categorías
            .stream()
            .map(Enum::name)
            .collect(Collectors.toList());
        }
}
