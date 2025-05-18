package com.tonilr.CarsEcommerce.Mappers;

import com.tonilr.CarsEcommerce.DTOs.CarDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import java.util.List;
import java.util.stream.Collectors;

public class CarMapper {

	// Convertir una entidad Car a un CarDTO
    public static CarDTO toCarDTO(Car car) {
        if (car == null) return null;

        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setMarca(car.getMarca());
        dto.setModelo(car.getModelo());
        dto.setPrecio(car.getPrecio());
        dto.setAño(car.getAño());
        dto.setCategoria(car.getCategoria());
        dto.setCombustible(car.getCombustible());
        dto.setTransmision(car.getTransmision());
        dto.setKilometraje(car.getKilometraje());
        dto.setDisponible(car.getDisponible());
        dto.setDescripcion(car.getDescripcion());
        dto.setImagenUrl(car.getImagenUrl());
        dto.setImages(car.getImages());
        dto.setOrders(car.getOrders().stream().map(order -> order.getId()).collect(Collectors.toSet()));
        dto.setReviews(car.getReviews().stream().map(review -> review.getId()).collect(Collectors.toSet()));
        dto.setCarts(car.getCarts().stream().map(cart -> cart.getId()).collect(Collectors.toSet()));
        return dto;
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
        car.setMarca(carDTO.getMarca());
        car.setModelo(carDTO.getModelo());
        car.setPrecio(carDTO.getPrecio());
        car.setAño(carDTO.getAño());
        car.setCategoria(carDTO.getCategoria());
        car.setCombustible(carDTO.getCombustible());
        car.setTransmision(carDTO.getTransmision());
        car.setKilometraje(carDTO.getKilometraje());
        car.setDisponible(carDTO.getDisponible());
        car.setDescripcion(carDTO.getDescripcion());
        car.setImagenUrl(carDTO.getImagenUrl());
        car.setImages(carDTO.getImages());
        return car;
    }

}
