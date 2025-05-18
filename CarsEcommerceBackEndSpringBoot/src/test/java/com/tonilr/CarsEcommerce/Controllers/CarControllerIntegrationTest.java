package com.tonilr.CarsEcommerce.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonilr.CarsEcommerce.DTOs.CarDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Repos.CarRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarRepo carRepo;

    private Car testCar;

    @BeforeEach
    void setUp() {
        carRepo.deleteAll();
        
        testCar = new Car();
        testCar.setMarca("Toyota");
        testCar.setModelo("Corolla");
        testCar.setPrecio(25000.0);
        testCar.setAño(2023);
        testCar.setCategoria("Sedan");
        testCar.setCombustible("Gasolina");
        testCar.setTransmision("Automático");
        testCar.setKilometraje(0);
        testCar.setDisponible(true);
        testCar.setDescripcion("Nuevo Toyota Corolla 2023");
        
        testCar = carRepo.save(testCar);
    }

    @Test
    void whenGetAllCars_thenReturnCarList() throws Exception {
        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca").value("Toyota"))
                .andExpect(jsonPath("$[0].modelo").value("Corolla"));
    }

    @Test
    void whenGetCarById_thenReturnCar() throws Exception {
        mockMvc.perform(get("/api/cars/{id}", testCar.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Toyota"))
                .andExpect(jsonPath("$.modelo").value("Corolla"));
    }

    @Test
    void whenGetCarById_withInvalidId_thenReturn404() throws Exception {
        mockMvc.perform(get("/api/cars/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreateCar_thenReturnCreatedCar() throws Exception {
        CarDTO newCarDTO = new CarDTO();
        newCarDTO.setMarca("Honda");
        newCarDTO.setModelo("Civic");
        newCarDTO.setPrecio(23000.0);
        newCarDTO.setAño(2023);
        newCarDTO.setCategoria("Sedan");
        newCarDTO.setCombustible("Gasolina");
        newCarDTO.setTransmision("Automático");
        newCarDTO.setKilometraje(0);
        newCarDTO.setDisponible(true);
        newCarDTO.setDescripcion("Nuevo Honda Civic 2023");

        mockMvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCarDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.marca").value("Honda"))
                .andExpect(jsonPath("$.modelo").value("Civic"));
    }

    @Test
    void whenUpdateCar_thenReturnUpdatedCar() throws Exception {
        CarDTO updatedCarDTO = new CarDTO();
        updatedCarDTO.setId(testCar.getId());
        updatedCarDTO.setMarca("Toyota");
        updatedCarDTO.setModelo("Corolla");
        updatedCarDTO.setPrecio(26000.0);
        updatedCarDTO.setAño(2023);
        updatedCarDTO.setCategoria("Sedan");
        updatedCarDTO.setCombustible("Gasolina");
        updatedCarDTO.setTransmision("Automático");
        updatedCarDTO.setKilometraje(0);
        updatedCarDTO.setDisponible(true);
        updatedCarDTO.setDescripcion("Toyota Corolla 2023 actualizado");

        mockMvc.perform(put("/api/cars/{id}", testCar.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCarDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio").value(26000.0))
                .andExpect(jsonPath("$.descripcion").value("Toyota Corolla 2023 actualizado"));
    }

    @Test
    void whenDeleteCar_thenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/cars/{id}", testCar.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/cars/{id}", testCar.getId()))
                .andExpect(status().isNotFound());
    }
} 