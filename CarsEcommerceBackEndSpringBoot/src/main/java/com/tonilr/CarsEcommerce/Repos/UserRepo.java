package com.tonilr.CarsEcommerce.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tonilr.CarsEcommerce.Entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    // Método para buscar un usuario por nombre de usuario
    List<User> findByUsernameIgnoreCase(String username);

    // Método para buscar un usuario por email
    Optional<User> findByEmail(String email);

    boolean existsByUsernameIgnoreCase(String username);
}
