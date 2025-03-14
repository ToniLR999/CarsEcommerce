package com.tonilr.CarsEcommerce.DTOs;

import java.util.List;

public class CartDTO {
    private Long id;
    private Long user;
    private List<Long> cars;

    public CartDTO() {}

    public CartDTO(Long id, Long user, List<Long> cars) {
        this.id = id;
        this.user = user;
        this.cars = cars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public List<Long> getCars() {
        return cars;
    }

    public void setCars(List<Long> cars) {
        this.cars = cars;
    }
}