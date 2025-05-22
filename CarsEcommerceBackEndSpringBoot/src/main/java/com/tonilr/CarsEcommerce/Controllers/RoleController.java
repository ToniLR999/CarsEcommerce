package com.tonilr.CarsEcommerce.Controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tonilr.CarsEcommerce.Entities.Role;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/role")
public class RoleController {
	
    @GetMapping("/all")
    public List<String> getAllRoles() {
        return Arrays.asList(Role.values())  // Devuelve una lista de categorías
            .stream()
            .map(Enum::name)
            .collect(Collectors.toList());
        }
}
