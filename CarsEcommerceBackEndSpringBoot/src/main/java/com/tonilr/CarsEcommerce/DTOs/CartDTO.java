package com.tonilr.CarsEcommerce.DTOs;

import java.util.List;

public class CartDTO {
    private Long userId;
    private List<Long> carIds;

    public CartDTO() {}

    public CartDTO(Long userId, List<Long> carIds) {
        this.userId = userId;
        this.carIds = carIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getCarIds() {
        return carIds;
    }

    public void setCarIds(List<Long> carIds) {
        this.carIds = carIds;
    }
}