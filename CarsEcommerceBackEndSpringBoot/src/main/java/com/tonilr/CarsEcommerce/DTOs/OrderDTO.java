package com.tonilr.CarsEcommerce.DTOs;

import java.util.List;

public class OrderDTO {
    private Long userId;
    private List<Long> carIds;
    private Double totalPrice;
    private String status;

    
    public OrderDTO() {}

    public OrderDTO(Long userId, List<Long> carIds, Double totalPrice , String status) {
        this.userId = userId;
        this.carIds = carIds;
        this.status = status;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user_id) {
        this.userId = user_id;
    }
    
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Long> getcarIds() {
        return carIds;
    }

    public void setcarIds(List<Long> carIdsList) {
        this.carIds = carIdsList;
    }
}
