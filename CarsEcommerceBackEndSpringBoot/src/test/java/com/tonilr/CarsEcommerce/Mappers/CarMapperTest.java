package com.tonilr.CarsEcommerce.Mappers;

import com.tonilr.CarsEcommerce.DTOs.CarDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarMapperTest {

    @Test
    void whenToCarDTO_thenReturnCarDTO() {
        // Arrange
        Car car = new Car();
        car.setId(1L);
        car.setMarca("Toyota");
        car.setModelo("Corolla");
        car.setPrecio(25000.0);
        car.setAño(2023);
        car.setCategoria("Sedan");
        car.setCombustible("Gasolina");
        car.setTransmision("Automático");
        car.setKilometraje(0);
        car.setDisponible(true);
        car.setDescripcion("Nuevo Toyota Corolla 2023");

        // Act
        CarDTO carDTO = CarMapper.toCarDTO(car);

        // Assert
        assertNotNull(carDTO);
        assertEquals(car.getId(), carDTO.getId());
        assertEquals(car.getMarca(), carDTO.getMarca());
        assertEquals(car.getModelo(), carDTO.getModelo());
        assertEquals(car.getPrecio(), carDTO.getPrecio());
        assertEquals(car.getAño(), carDTO.getAño());
        assertEquals(car.getCategoria(), carDTO.getCategoria());
        assertEquals(car.getCombustible(), carDTO.getCombustible());
        assertEquals(car.getTransmision(), carDTO.getTransmision());
        assertEquals(car.getKilometraje(), carDTO.getKilometraje());
        assertEquals(car.getDisponible(), carDTO.getDisponible());
        assertEquals(car.getDescripcion(), carDTO.getDescripcion());
    }

    @Test
    void whenToCarDTOList_thenReturnCarDTOList() {
        // Arrange
        Car car1 = new Car();
        car1.setId(1L);
        car1.setMarca("Toyota");

        Car car2 = new Car();
        car2.setId(2L);
        car2.setMarca("Honda");

        List<Car> cars = Arrays.asList(car1, car2);

        // Act
        List<CarDTO> carDTOs = CarMapper.toCarDTO(cars);

        // Assert
        assertNotNull(carDTOs);
        assertEquals(2, carDTOs.size());
        assertEquals("Toyota", carDTOs.get(0).getMarca());
        assertEquals("Honda", carDTOs.get(1).getMarca());
    }

    @Test
    void whenToCarEntity_thenReturnCar() {
        // Arrange
        CarDTO carDTO = new CarDTO();
        carDTO.setId(1L);
        carDTO.setMarca("Toyota");
        carDTO.setModelo("Corolla");
        carDTO.setPrecio(25000.0);
        carDTO.setAño(2023);
        carDTO.setCategoria("Sedan");
        carDTO.setCombustible("Gasolina");
        carDTO.setTransmision("Automático");
        carDTO.setKilometraje(0);
        carDTO.setDisponible(true);
        carDTO.setDescripcion("Nuevo Toyota Corolla 2023");

        // Act
        Car car = CarMapper.toCarEntity(carDTO);

        // Assert
        assertNotNull(car);
        assertEquals(carDTO.getId(), car.getId());
        assertEquals(carDTO.getMarca(), car.getMarca());
        assertEquals(carDTO.getModelo(), car.getModelo());
        assertEquals(carDTO.getPrecio(), car.getPrecio());
        assertEquals(carDTO.getAño(), car.getAño());
        assertEquals(carDTO.getCategoria(), car.getCategoria());
        assertEquals(carDTO.getCombustible(), car.getCombustible());
        assertEquals(carDTO.getTransmision(), car.getTransmision());
        assertEquals(carDTO.getKilometraje(), car.getKilometraje());
        assertEquals(carDTO.getDisponible(), car.getDisponible());
        assertEquals(carDTO.getDescripcion(), car.getDescripcion());
    }

    @Test
    void whenToCarDTO_withNullCar_thenReturnNull() {
        assertNull(CarMapper.toCarDTO((Car) null));
    }

    @Test
    void whenToCarEntity_withNullCarDTO_thenReturnNull() {
        assertNull(CarMapper.toCarEntity(null));
    }
} 