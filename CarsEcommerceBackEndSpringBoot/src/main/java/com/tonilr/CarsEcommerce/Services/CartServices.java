package com.tonilr.CarsEcommerce.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.DTOs.CartDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Entities.Cart;
import com.tonilr.CarsEcommerce.Entities.User;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.CarRepo;
import com.tonilr.CarsEcommerce.Repos.CartRepo;
import com.tonilr.CarsEcommerce.Repos.UserRepo;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CartServices {

	@Autowired
	private final CartRepo cartRepo;
	
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private CarRepo carRepository;

	public CartServices(CartRepo cartRepo) {
		this.cartRepo = cartRepo;
	}
	
	public Cart addCartForUser(Cart cart) {
		return cartRepo.save(cart);
	}	


	public Cart addCart(CartDTO cartDTO) {
		User user = userRepository.findById(cartDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Car> cars = new ArrayList<>(carRepository.findAllById(cartDTO.getCars()));

        if (cars.isEmpty()) {
            throw new RuntimeException("No cars found for the given IDs");
        }

        Cart cart = new Cart();

        cart.setUser(user);
        cart.setCars(cars);
		
		return cartRepo.save(cart);
	}	

	public List<Cart> findAllCarts() {
		return cartRepo.findAll();
	}
	
	@Transactional
    public Cart updateCart(CartDTO cartDTO) {
		User user = userRepository.findById(cartDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Car> cars = new ArrayList<>(carRepository.findAllById(cartDTO.getCars()));

        if (cars.isEmpty()) {
            throw new RuntimeException("No cars found for the given IDs");
        }

        Cart cart = cartRepo.findById(cartDTO.getId())
                .orElseThrow(() -> new RuntimeException("Review not found"));
        
        cart.setUser(user);
        cart.setCars(cars);

        return cartRepo.save(cart);
    }
	
	@Transactional
    public Cart updateCartForUser(CartDTO cartDTO) {
        User user = userRepository.findById(cartDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Car> cars = new ArrayList<>(carRepository.findAllById(cartDTO.getCars()));

        if (cars.isEmpty()) {
            throw new RuntimeException("No cars found for the given IDs");
        }

        Cart cart = cartRepo.findByUser(user)
                .orElse(new Cart()); // Si el usuario no tiene carrito, se crea uno nuevo

        cart.setUser(user);
        cart.setCars(cars);

        return cartRepo.save(cart);
    }
	
    public Cart getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for this user"));
    }

	public Cart findCartById(Long id) {
		return cartRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Cart by id " + id + " was not found"));

	}

	public void deleteCart(Long id) {
		cartRepo.deleteById(id);
	}
}
