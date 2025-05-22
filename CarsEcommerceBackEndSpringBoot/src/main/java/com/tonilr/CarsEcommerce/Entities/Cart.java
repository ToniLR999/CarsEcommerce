package com.tonilr.CarsEcommerce.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate
public class Cart {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<Car> cars = new ArrayList<Car>();
	
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
    
    // Getters and Setters
    
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now(); // Establecer la fecha y hora de creación
        }
        
        if (this.createdBy == null) {
            this.createdBy = "System"; // Establecer el creador (esto puede depender del contexto de tu aplicación)
        }
    }

    public Long getId() {
        return cart_id;
    }

    public void setId(Long cart_id) {
        this.cart_id = cart_id;
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
            car.getCarts().add(this);  // Solo esta línea actualiza la otra entidad
        }
    }
    
    
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
