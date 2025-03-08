package com.tonilr.CarsEcommerce.DTOs;


public class ReviewDTO {
	
    private Long userId;
    private Long carId;
    private Integer rating;
    private String comment;

    public ReviewDTO() {}

    public ReviewDTO(Long userId, Long carId, Integer rating, String comment) {
        this.userId = userId;
        this.carId = carId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
