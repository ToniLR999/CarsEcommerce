package com.tonilr.CarsEcommerce.DTOs;


public class ReviewDTO {
	
    private Long Id;
    private Long userId;
    private Long carId;
    private Integer rating;
    private String comment;

    public ReviewDTO() {}

    public ReviewDTO(Long Id, Long userId, Long carId, Integer rating, String comment) {
        this.Id = Id;
        this.userId = userId;
        this.carId = carId;
        this.rating = rating;
        this.comment = comment;
    }
    
    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
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
