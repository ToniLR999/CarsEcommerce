package com.tonilr.CarsEcommerce.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cars")
public class Car {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer año;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String combustible;

    @Column(nullable = false)
    private String transmision;

    @Column(nullable = false)
    private Integer kilometraje;

    @Column(nullable = false)
    private Boolean disponible;

    @Column(length = 1000)
    private String descripcion;

    @Column
    private String imagenUrl;

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
    
    @ElementCollection
    private List<String> images  = new ArrayList<>(); 
    
    @JsonIgnore // El dueño de la relación que sí se serializa
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<Order>();  
    
    @JsonIgnore // El dueño de la relación que sí se serializa
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<Review>();
    
    @JsonIgnore // El dueño de la relación que sí se serializa
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Cart> carts = new HashSet<Cart>();
    
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
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
    
    // Método para agregar una imagen a la lista
    public void addImage(String imageUrl) {
        this.images.add(imageUrl);
    }
    
    public void removeImage(String imageUrl) {
        this.images.remove(imageUrl);
    }
    
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    
    
    public void addOrder(Order order) {
    	
        if (this.orders == null) {
            this.orders = new HashSet<>();
        }
        
        if (!this.orders.contains(order)) {
            this.orders.add(order);
        }
    }
    
    
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
    
    public void addReview(Review review) {
        if (this.reviews == null) {
            this.reviews = new HashSet<>();
        }
        
        if (!this.reviews.contains(review)) {
        
        this.reviews.add(review);  // Añade el order al set de orders
        review.setCar(this);// Establece el user en el order para mantener la relación bidireccional
        }
      }
    
    
    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }
    
    public void addCart(Cart cart) {
    	
        if (this.carts == null) {
            this.carts = new HashSet<>();
        }
        
        if (!this.carts.contains(cart)) {
            this.carts.add(cart);
        }
    }

    
    
}
