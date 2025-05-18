package com.tonilr.CarsEcommerce.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonilr.CarsEcommerce.DTOs.CarSearchCriteria;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Repos.CarRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarSearchControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarRepo carRepo;

    @BeforeEach
    void setUp() {
        carRepo.deleteAll();
        
        // Crear coches de prueba
        Car car1 = new Car();
        car1.setMarca("Toyota");
        car1.setModelo("Corolla");
        car1.setPrecio(25000.0);
        car1.setAño(2023);
        car1.setCategoria("Sedan");
        car1.setCombustible("Gasolina");
        car1.setTransmision("Automático");
        car1.setKilometraje(0);
        car1.setDisponible(true);
        car1.setDescripcion("Nuevo Toyota Corolla 2023");
        
        Car car2 = new Car();
        car2.setMarca("Honda");
        car2.setModelo("Civic");
        car2.setPrecio(23000.0);
        car2.setAño(2022);
        car2.setCategoria("Sedan");
        car2.setCombustible("Híbrido");
        car2.setTransmision("Automático");
        car2.setKilometraje(10000);
        car2.setDisponible(true);
        car2.setDescripcion("Honda Civic 2022");
        
        carRepo.save(car1);
        carRepo.save(car2);
    }

    @Test
    void whenSearchByMarca_thenReturnMatchingCars() throws Exception {
        CarSearchCriteria criteria = new CarSearchCriteria();
        criteria.setMarca("Toyota");

        mockMvc.perform(post("/api/cars/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].marca").value("Toyota"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void whenSearchByPriceRange_thenReturnMatchingCars() throws Exception {
        CarSearchCriteria criteria = new CarSearchCriteria();
        criteria.setPrecioMin(24000.0);
        criteria.setPrecioMax(26000.0);

        mockMvc.perform(post("/api/cars/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].marca").value("Toyota"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void whenSearchByYearRange_thenReturnMatchingCars() throws Exception {
        CarSearchCriteria criteria = new CarSearchCriteria();
        criteria.setAñoMin(2023);
        criteria.setAñoMax(2023);

        mockMvc.perform(post("/api/cars/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].marca").value("Toyota"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void whenSearchByMultipleCriteria_thenReturnMatchingCars() throws Exception {
        CarSearchCriteria criteria = new CarSearchCriteria();
        criteria.setMarca("Honda");
        criteria.setPrecioMin(22000.0);
        criteria.setPrecioMax(24000.0);
        criteria.setAñoMin(2022);
        criteria.setAñoMax(2022);

        mockMvc.perform(post("/api/cars/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].marca").value("Honda"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void whenSearchWithNoResults_thenReturnEmptyList() throws Exception {
        CarSearchCriteria criteria = new CarSearchCriteria();
        criteria.setMarca("BMW");

        mockMvc.perform(post("/api/cars/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0));
    }
} 