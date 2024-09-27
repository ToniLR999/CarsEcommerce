package com.tonilr.CarsEcommerce.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tonilr.CarsEcommerce.Entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>{

}
