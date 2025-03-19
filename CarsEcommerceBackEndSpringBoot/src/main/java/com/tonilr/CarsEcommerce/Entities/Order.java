package com.tonilr.CarsEcommerce.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate
public class Order {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;  // PENDING, PROCESSING, SHIPPED, DELIVERED

    @Column(nullable = false)
    private Double totalPrice;
    
	@CreatedDate
    @Column(nullable = true, updatable = false)
    private LocalDateTime createdAt;
    
    @CreatedBy
    @Column(nullable = true)
    private String createdBy;
    
    @LastModifiedDate
    @Column(nullable = true, updatable = true)
    private LocalDateTime updatedAt;
    
    @LastModifiedBy
    @Column(nullable = true)
    private String updatedBy;
    
    @Column
    private Date deletedAt;
    
    @Column
    private String deletedBy;
        
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Car> cars = new ArrayList<Car>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // Getters and Setters

    public Long getId() {
        return order_id;
    }

    public void setId(Long order_id) {
        this.order_id = order_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
    
    
    public void addCar(Car car) {
        if (this.cars == null) {
            this.cars = new ArrayList<>();
        }
    	
        if (!this.cars.contains(car)) {
            this.cars.add(car);
            car.getOrders().add(this);  // Solo esta línea actualiza la otra entidad
        }
    }
    

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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
