package com.tonilr.CarsEcommerce.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tonilr.CarsEcommerce.Entities.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review,Long>{

}
