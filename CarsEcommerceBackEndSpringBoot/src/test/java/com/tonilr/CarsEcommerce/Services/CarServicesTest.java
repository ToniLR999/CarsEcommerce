package com.tonilr.CarsEcommerce.Services;

import com.tonilr.CarsEcommerce.DTOs.CarDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.CarRepo;
import com.tonilr.CarsEcommerce.Repos.CartRepo;
import com.tonilr.CarsEcommerce.Repos.OrderRepo;
import com.tonilr.CarsEcommerce.Repos.ReviewRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarServicesTest {

    @Mock
    private CarRepo carRepo;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private ReviewRepo reviewRepo;

    @InjectMocks
    private CarServices carServices;

    private Car car;
    private CarDTO carDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Configurar datos de prueba
        car = new Car();
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

        carDTO = new CarDTO();
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
    }

    @Test
    void whenSaveCar_thenReturnSavedCar() {
        when(carRepo.save(any(Car.class))).thenReturn(car);

        Car savedCar = carServices.saveCar(car);

        assertNotNull(savedCar);
        assertEquals("Toyota", savedCar.getMarca());
        assertEquals("Corolla", savedCar.getModelo());
        verify(carRepo, times(1)).save(any(Car.class));
    }

    @Test
    void whenAddCar_thenReturnNewCar() {
        when(carRepo.save(any(Car.class))).thenReturn(car);

        Car addedCar = carServices.addCar(carDTO);

        assertNotNull(addedCar);
        assertEquals("Toyota", addedCar.getMarca());
        assertEquals("Corolla", addedCar.getModelo());
        verify(carRepo, times(1)).save(any(Car.class));
    }

    @Test
    void whenFindAllCars_thenReturnListOfCars() {
        List<Car> cars = Arrays.asList(car);
        when(carRepo.findAll()).thenReturn(cars);

        List<Car> foundCars = carServices.findAllCars();

        assertNotNull(foundCars);
        assertEquals(1, foundCars.size());
        verify(carRepo, times(1)).findAll();
    }

    @Test
    void whenFindCarById_thenReturnCar() {
        when(carRepo.findById(1L)).thenReturn(Optional.of(car));

        Car foundCar = carServices.findCarById(1L);

        assertNotNull(foundCar);
        assertEquals("Toyota", foundCar.getMarca());
        verify(carRepo, times(1)).findById(1L);
    }

    @Test
    void whenFindCarById_withInvalidId_thenThrowNotFoundException() {
        when(carRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            carServices.findCarById(999L);
        });
    }

    @Test
    void whenUpdateCar_thenReturnUpdatedCar() {
        when(carRepo.findById(1L)).thenReturn(Optional.of(car));
        when(carRepo.save(any(Car.class))).thenReturn(car);

        Car updatedCar = carServices.updateCar(carDTO);

        assertNotNull(updatedCar);
        assertEquals("Toyota", updatedCar.getMarca());
        verify(carRepo, times(1)).findById(1L);
        verify(carRepo, times(1)).save(any(Car.class));
    }

    @Test
    void whenDeleteCar_thenCarIsDeleted() {
        when(carRepo.findById(1L)).thenReturn(Optional.of(car));
        doNothing().when(carRepo).deleteById(1L);

        carServices.deleteCar(1L);

        verify(carRepo, times(1)).findById(1L);
        verify(carRepo, times(1)).deleteById(1L);
    }
} 