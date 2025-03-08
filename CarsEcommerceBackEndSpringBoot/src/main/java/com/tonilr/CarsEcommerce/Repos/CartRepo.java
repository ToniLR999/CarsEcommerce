package com.tonilr.CarsEcommerce.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tonilr.CarsEcommerce.Entities.Cart;
import com.tonilr.CarsEcommerce.Entities.User;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long>{
    Optional<Cart> findByUser(User user);
}
