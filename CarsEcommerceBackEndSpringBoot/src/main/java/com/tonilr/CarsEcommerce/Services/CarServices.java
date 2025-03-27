package com.tonilr.CarsEcommerce.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.DTOs.CarDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Entities.CarCategory;
import com.tonilr.CarsEcommerce.Entities.Cart;
import com.tonilr.CarsEcommerce.Entities.Order;
import com.tonilr.CarsEcommerce.Entities.Review;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.CarRepo;
import com.tonilr.CarsEcommerce.Repos.CartRepo;
import com.tonilr.CarsEcommerce.Repos.OrderRepo;
import com.tonilr.CarsEcommerce.Repos.ReviewRepo;

@Service
public class CarServices {

	@Autowired
	private final CarRepo carRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
	private OrderRepo orderRepo;

	public CarServices(CarRepo carRepo) {
		this.carRepo = carRepo;
	}
	
    public Car saveCar(Car car) {
        return carRepo.save(car);
    }

	public Car addCar(CarDTO carDTO) {
		Car car = new Car();
		car.setName(carDTO.getName());
		car.setDescription(carDTO.getDescription());
		car.setPrice(carDTO.getPrice());
		car.setStock(carDTO.getStock());
		car.setImages(carDTO.getImages());
		car.setCategory(CarCategory.valueOf(carDTO.getCategory()));
		
        Set<Cart> carts = new HashSet<>(cartRepo.findAllById(carDTO.getCarts()));
		car.setCarts(carts);
		
        Set<Order> orders = new HashSet<>(orderRepo.findAllById(carDTO.getOrders()));
		car.setOrders(orders);
		
        Set<Review> reviews = new HashSet<>(reviewRepo.findAllById(carDTO.getReviews()));
		car.setReviews(reviews);
		
		
		return carRepo.save(car);
	}

	public List<Car> findAllCars() {
		return carRepo.findAll();
	}

	public Car updateCar(CarDTO carDTO) {
		
	    if (carDTO.getId() == null) {
	        throw new RuntimeException("Car ID cannot be null");
	    }
	    
	    
	    Car car = carRepo.findById(carDTO.getId())
	              .orElseThrow(() -> new RuntimeException("Car not found"));
	       
		car.setName(carDTO.getName());
		car.setDescription(carDTO.getDescription());
		car.setPrice(carDTO.getPrice());
		car.setStock(carDTO.getStock());
		car.setImages(carDTO.getImages());
		car.setCategory(carDTO.getCategory() != null ? CarCategory.valueOf(carDTO.getCategory()) : car.getCategory());

		
        if (carDTO.getOrders() != null && !carDTO.getOrders().isEmpty() ) {      	
            Set<Order> orders = new HashSet<>(orderRepo.findAllById(carDTO.getOrders()));
            if (orders.size() != carDTO.getOrders().size()) {
                throw new RuntimeException("Some orders could not be found");
            }
            
            car.getOrders().clear();  
            car.getOrders().addAll(orders);
        } else {
            // Si orderIds es nulo o vacío, puedes establecer un conjunto vacío
            car.getOrders().clear();
        }
        
        
        if (carDTO.getCarts() != null && !carDTO.getCarts().isEmpty() ) {      	
            Set<Cart> carts = new HashSet<>(cartRepo.findAllById(carDTO.getCarts()));
            if (carts.size() != carDTO.getCarts().size()) {
                throw new RuntimeException("Some carts could not be found");
            }
            
            car.getCarts().clear();  
            car.getCarts().addAll(carts);
        }else {
            // Si orderIds es nulo o vacío, puedes establecer un conjunto vacío
            car.getCarts().clear();
        }
        
        
        if (carDTO.getReviews() != null && !carDTO.getReviews().isEmpty() ) {      	
            Set<Review> reviews = new HashSet<>(reviewRepo.findAllById(carDTO.getReviews()));
            if (reviews.size() != carDTO.getReviews().size()) {
                throw new RuntimeException("Some reviews could not be found");
            }
            
            car.getReviews().clear();  
            car.getReviews().addAll(reviews);
        }else {
            // Si orderIds es nulo o vacío, puedes establecer un conjunto vacío
            car.getReviews().clear();
        }
		

		return carRepo.save(car);
	}

	public Car findCarById(Long id) {
		return carRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Car by id " + id + " was not found"));

	}

	public void deleteCar(Long id) {
	    Car car = carRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Car not found"));

	    // Eliminar las relaciones con los carritos (Tabla intermedia car_carts)
	    for (Cart cart : car.getCarts()) {
	        cart.getCars().remove(car);
	        
	    }
	    
        // Eliminamos las relaciones en cars_carts

    

	    // Eliminar las relaciones con las órdenes (Tabla intermedia cars_orders)
	    for (Order order : car.getOrders()) {
	        order.getCars().remove(car);
	    }

	    // Eliminar las reseñas asociadas al carro
	    for (Review review : car.getReviews()) {
	        reviewRepo.delete(review);
	    }
	    
		carRepo.deleteById(id);
	}
}
