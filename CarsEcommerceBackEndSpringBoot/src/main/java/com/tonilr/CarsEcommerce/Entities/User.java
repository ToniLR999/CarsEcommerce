package com.tonilr.CarsEcommerce.Entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
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
	
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Order> order  = new HashSet<Order>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Review> review = new HashSet<Review>();
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
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

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }
    

    public Set<Review> getReview() {
        return review;
    }

    public void setReview(Set<Review> review) {
        this.review = review;
    }
    

    public Cart getRCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}