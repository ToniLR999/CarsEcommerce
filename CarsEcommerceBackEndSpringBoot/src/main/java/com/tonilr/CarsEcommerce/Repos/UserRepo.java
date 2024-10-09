package com.tonilr.CarsEcommerce.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tonilr.CarsEcommerce.Entities.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{

    // Método para buscar un usuario por nombre de usuario
    Optional<User> findByUsername(String username);
}
