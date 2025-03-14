package com.tonilr.CarsEcommerce.Entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonPropertyOrder({ "id", "rating", "comment", "createdAt", "user", "car" }) // Especificar el orden
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
	    private Date createdAt;

	    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;
	    
	    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "car_id")
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

	    public Date getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(Date localDateTime) {
	        this.createdAt = localDateTime;
	    }
}
