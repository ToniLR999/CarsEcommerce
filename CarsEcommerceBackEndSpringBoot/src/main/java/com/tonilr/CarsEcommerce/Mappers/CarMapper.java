package com.tonilr.CarsEcommerce.Mappers;

import com.tonilr.CarsEcommerce.DTOs.CarDTO;
import com.tonilr.CarsEcommerce.DTOs.UserDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Entities.CarCategory;
import com.tonilr.CarsEcommerce.Entities.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CarMapper {

	// Convertir una entidad Car a un CarDTO
    public static CarDTO toCarDTO(Car car) {
        if (car == null) return null;

        return new CarDTO(
                car.getId(),
                car.getName(),
                car.getDescription(),
                car.getPrice(),
                car.getStock(),
                car.getCategory() != null ? car.getCategory().name() : null,
                car.getImages(),
                car.getOrders().stream().map(order -> order.getId()).collect(Collectors.toSet()),
                car.getReviews().stream().map(review -> review.getId()).collect(Collectors.toSet()),
                car.getCarts().stream().map(cart -> cart.getId()).collect(Collectors.toSet())
        );
    }

    // Convertir una lista de Cars a una lista de CarDTOs
    public static List<CarDTO> toCarDTO(List<Car> cars) {
        return cars.stream().map(CarMapper::toCarDTO).collect(Collectors.toList());
    }

    // Convertir un CarDTO a una entidad Car (para crear o actualizar)
    public static Car toCarEntity(CarDTO carDTO) {
        if (carDTO == null) return null;

        Car car = new Car();
        car.setId(carDTO.getId());
        car.setName(carDTO.getName());
        car.setDescription(carDTO.getDescription());
        car.setPrice(carDTO.getPrice());
        car.setStock(carDTO.getStock());
        car.setCategory(carDTO.getCategory() != null ? CarCategory.valueOf(carDTO.getCategory()) : null);
        car.setImages(carDTO.getImages());

        return car;  // No seteamos orders, reviews ni carts aquí, eso es para los servicios
    }

}
