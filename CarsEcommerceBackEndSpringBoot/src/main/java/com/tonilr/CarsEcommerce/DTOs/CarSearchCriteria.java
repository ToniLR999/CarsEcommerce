package com.tonilr.CarsEcommerce.DTOs;

import lombok.Data;

@Data
public class CarSearchCriteria {
    private String marca;
    private String modelo;
    private Double precioMin;
    private Double precioMax;
    private Integer añoMin;
    private Integer añoMax;
    private String categoria;
    private String combustible;
    private String transmision;
    private Integer kilometrajeMax;
    private Boolean disponible;
} 