package com.tonilr.CarsEcommerce.DTOs;

import java.util.List;

public class OrderDTO {
    private Long userId;
    private List<Long> carIds;
    
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }

    public List<Long> getcarIds() {
        return carIds;
    }

    public void setcarIds(List<Long> carIdsList) {
        this.carIds = carIdsList;
    }
}
