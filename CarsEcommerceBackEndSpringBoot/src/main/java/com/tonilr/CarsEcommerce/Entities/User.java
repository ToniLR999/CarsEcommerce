package com.tonilr.CarsEcommerce.Entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@EntityListeners(AuditingEntityListener.class) // Asegura que la auditoría escuche esta entidad
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades de Hibernate
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
    private Long user_id;

	@Column(nullable = false, updatable = true)
    private String username;

	@Column(nullable = false, updatable = true)
    private String password;

	@Column(nullable = false, updatable = true)
    private String email;
    
	@Column(nullable = false, updatable = true)
	private Date register_date;
		
	@Column(nullable = true, updatable = true)
    private String phoneNumber;
	
	@Column(nullable = false, updatable = true)
    private Boolean isActive;

	@Column(nullable = false, updatable = true)
    private Role role;  // Example: ADMIN, USER
    
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
    
	@JsonIgnore // El dueño de la relación que sí se serializa
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Order> orders  = new HashSet<Order>();
    
    @JsonIgnore // El dueño de la relación que sí se serializa
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<Review>();
    
    @JsonIgnore // El dueño de la relación que sí se serializa
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "cart_id", nullable = true)
    private Cart cart;

    // Getters and Setters

    public Long getId() {
        return user_id;
    }

    public void setId(Long user_id) {
        this.user_id = user_id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
    
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> order) {
        this.orders = order;
    }
    
    public void addOrder(Order order) {
        if (this.orders == null) {
            this.orders = new HashSet<>();
        }
        
        if (!this.orders.contains(order)) {

        this.orders.add(order);  // Añade el order al set de orders
        order.setUser(this);  // Establece el user en el order para mantener la relación bidireccional
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
        review.setUser(this);  // Establece el user en el order para mantener la relación bidireccional
        }
   }
    

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
