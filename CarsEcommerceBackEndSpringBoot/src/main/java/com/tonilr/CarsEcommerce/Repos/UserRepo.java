package com.tonilr.CarsEcommerce.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tonilr.CarsEcommerce.Entities.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{

}
