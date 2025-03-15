package com.tonilr.CarsEcommerce.DTOs;

import java.util.List;
import java.util.Set;

public class CarDTO {

	   	private Long id;
	    private String name;
	    private String description;
	    private Double price;
		private Integer stock;
	    private String category;
	    private List<String> images; 
	    private Set<Long> orders;
	    private Set<Long> reviews;
	    private Set<Long> carts;
	    
	    public CarDTO() {}

	    public CarDTO(Long id, String name, String description, Double price, Integer stock, String category, List<String> images, Set<Long> orders, Set<Long> reviews, Set<Long> carts) {
	        this.id = id;
	        this.name = name;
	        this.description = description;
	        this.price = price;
	        this.stock = stock;
	        this.category = category;
	        this.images = images;
	        this.orders = orders;
	        this.reviews = reviews;
	        this.carts = carts;
	    }


	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	    
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
	    
	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }
	    
	    public Double getPrice() {
	        return price;
	    }

	    public void setPrice(Double price) {
	        this.price = price;
	    }
	    
	    public Integer getStock() {
	        return stock;
	    }

	    public void setStock(Integer stock) {
	        this.stock = stock;
	    }
	    
	    public String getCategory() {
	        return category;
	    }

	    public void setCategory(String category) {
	        this.category = category;
	    }
	    
	    
	    
	    public List<String> getImages() {
	        return images;
	    }

	    public void setImages(List<String> images) {
	        this.images = images;
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
	    
	    public Set<Long> getCarts() {
	        return carts;
	    }

	    public void setCarts(Set<Long> carts) {
	        this.carts = carts;
	    }
	
}
