package com.tonilr.CarsEcommerce.Entities;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "reviews")
public class Review {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private Integer rating;  // Rating from 1 to 5
	    
	    @Column(nullable = false)
	    private String comment;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(nullable = false)
	    private LocalDateTime createdAt;

	    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;
	    
	    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "car_id", nullable = false)
	    private Car car;
	    
	    // Getters and Setters

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	    public Car getCar() {
	        return car;
	    }

	    public void setCar(Car car) {
	        this.car = car;
	    }

	    public String getComment() {
	        return comment;
	    }

	    public void setComment(String comment) {
	        this.comment = comment;
	    }

	    public Integer getRating() {
	        return rating;
	    }

	    public void setRating(Integer rating) {
	        this.rating = rating;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime localDateTime) {
	        this.createdAt = localDateTime;
	    }
}
