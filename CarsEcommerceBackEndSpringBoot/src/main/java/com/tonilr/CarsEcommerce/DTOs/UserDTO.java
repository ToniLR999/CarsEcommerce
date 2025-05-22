package com.tonilr.CarsEcommerce.DTOs;

import java.util.Date;
import java.util.Set;
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
	private Date register_date;
    private String phoneNumber;
    private Boolean isActive;
    private String role;
    private Set<Long> orders;
    private Set<Long> reviews;
    private Long cart;

    public UserDTO() {}

    public UserDTO(Long id, String username, String email, Date register_date, String phoneNumber, Boolean isActive, String role, Set<Long> orders, Set<Long> reviews, Long cart) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.register_date = register_date;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.role = role;
        this.orders = orders;
        this.reviews = reviews;
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getRegisterDate() {
        return register_date;
    }

    public void setRegisterDate(Date register_date) {
        this.register_date = register_date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Long> getOrders() {
        return orders;
    }

    public void setOrders(Set<Long> orders) {
        this.orders = orders;
    }

    public Set<Long> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Long> reviews) {
        this.reviews = reviews;
    }

    public Long getCart() {
        return cart;
    }

    public void setCart(Long cart) {
        this.cart = cart;
    }
}