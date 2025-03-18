package com.tonilr.CarsEcommerce.Entities;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

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

		@CreatedDate
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(nullable = false)
	    private LocalDateTime createdAt;
	    
	    @CreatedBy
	    @Column(nullable = false)
	    private String createdBy;
	    
	    @LastModifiedDate
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column
	    private LocalDateTime updatedAt;
	    
	    @LastModifiedBy
	    @Column
	    private String updatedBy;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column
	    private Date deletedAt;
	    
	    @Column
	    private String deletedBy;

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

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime localDateTime) {
	        this.createdAt = localDateTime;
	    }
	    
	    public String getCreatedBy() {
	        return createdBy;
	    }

	    public void setCreatedBy(String createdBy) {
	        this.createdBy = createdBy;
	    }
	    
	    public LocalDateTime getUpdatedAt() {
	        return updatedAt;
	    }

	    public void setUpdatedAt(LocalDateTime updatedAt) {
	        this.updatedAt = updatedAt;
	    }
	    
	    public String getUpdatedBy() {
	        return updatedBy;
	    }

	    public void setUpdatedBy(String updatedBy) {
	        this.updatedBy = updatedBy;
	    }
	    
	    public Date getDeletedAt() {
	        return deletedAt;
	    }

	    public void setDeletedAt(Date deletedAt) {
	        this.deletedAt = deletedAt;
	    }

	    public String getDeletedBy() {
	        return deletedBy;
	    }

	    public void DeletedBy(String deletedBy) {
	        this.deletedBy = deletedBy;
	    }
	    
}
