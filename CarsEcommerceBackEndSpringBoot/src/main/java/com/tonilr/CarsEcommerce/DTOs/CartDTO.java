package com.tonilr.CarsEcommerce.DTOs;

import java.util.List;

public class CartDTO {
    private Long cartId;
    private Long userId;
    private List<Long> carIds;

    public CartDTO() {}

    public CartDTO(Long cartId, Long userId, List<Long> carIds) {
        this.cartId = cartId;
        this.userId = userId;
        this.carIds = carIds;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
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