package com.tonilr.CarsEcommerce.Repos;

import com.tonilr.CarsEcommerce.Entities.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    
    @Query("SELECT c FROM Car c WHERE " +
           "(:marca IS NULL OR c.marca LIKE %:marca%) AND " +
           "(:modelo IS NULL OR c.modelo LIKE %:modelo%) AND " +
           "(:precioMin IS NULL OR c.precio >= :precioMin) AND " +
           "(:precioMax IS NULL OR c.precio <= :precioMax) AND " +
           "(:añoMin IS NULL OR c.año >= :añoMin) AND " +
           "(:añoMax IS NULL OR c.año <= :añoMax) AND " +
           "(:categoria IS NULL OR c.categoria = :categoria) AND " +
           "(:combustible IS NULL OR c.combustible = :combustible) AND " +
           "(:transmision IS NULL OR c.transmision = :transmision) AND " +
           "(:kilometrajeMax IS NULL OR c.kilometraje <= :kilometrajeMax) AND " +
           "(:disponible IS NULL OR c.disponible = :disponible)")
    Page<Car> findBySearchCriteria(
        @Param("marca") String marca,
        @Param("modelo") String modelo,
        @Param("precioMin") Double precioMin,
        @Param("precioMax") Double precioMax,
        @Param("añoMin") Integer añoMin,
        @Param("añoMax") Integer añoMax,
        @Param("categoria") String categoria,
        @Param("combustible") String combustible,
        @Param("transmision") String transmision,
        @Param("kilometrajeMax") Integer kilometrajeMax,
        @Param("disponible") Boolean disponible,
        Pageable pageable
    );
} 