package com.tonilr.CarsEcommerce.Services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.DTOs.CartDTO;
import com.tonilr.CarsEcommerce.DTOs.UserDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Entities.Cart;
import com.tonilr.CarsEcommerce.Entities.Order;
import com.tonilr.CarsEcommerce.Entities.Review;
import com.tonilr.CarsEcommerce.Entities.Role;
import com.tonilr.CarsEcommerce.Entities.User;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.CartRepo;
import com.tonilr.CarsEcommerce.Repos.OrderRepo;
import com.tonilr.CarsEcommerce.Repos.ReviewRepo;
import com.tonilr.CarsEcommerce.Repos.UserRepo;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserServices {

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private final UserRepo userRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private ReviewRepo reviewRepo;
	
	@Autowired
    private CartServices cartServices;  // Servicio de Carrito

	public UserServices(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	@Transactional																						
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encriptamos la contraseña
        user.setRegisterDate(new Date());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setIsActive(true);
        
        user.setRole(userDTO.getRole() != null ? Role.valueOf(userDTO.getRole()) : Role.USER);

		
		user = userRepo.save(user);
		 if (userDTO.getCart() == null) {
	            Cart newCart = new Cart();
	            newCart.setUser(user);
	            newCart = cartServices.addCartForUser(newCart);  // Guardar el carrito en la base de datos
	            user.setCart(newCart); // Asignar el carrito al usuario
	            	            

	            userRepo.save(user);	
	        }else {

	            Cart existentCart = cartServices.findCartById(userDTO.getCart());  // Guardar el carrito en la base de datos
	            user.setCart(existentCart); // Asignar el carrito al usuario
	            existentCart.setUser(user);

	            // ✅ Convertir `existingCart` a `CartDTO` antes de pasarlo a `updateCart`
	            CartDTO existingCartDTO = new CartDTO(existentCart.getId(), existentCart.getUser().getId(),
	            		existentCart.getCars().stream().map(Car::getId).collect(Collectors.toList()));

	            cartServices.updateCartForUser(existingCartDTO);
	            }
		
	        return userRepo.save(user);
    }
	
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        // Verificar si la contraseña ingresada coincide con la almacenada
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	public User updateUser(UserDTO userDTO) {
		System.out.print("UserDTO recibido: "+ userDTO.getId()+ " - "+userDTO.getCart()+ " - "+userDTO.getReviews()+" - "+userDTO.getOrders());
		
		
	    if (userDTO.getId() == null) {
	        throw new RuntimeException("User ID cannot be null");
	    }
        User user = userRepo.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        

        // Verifica que el cartId no sea nulo
        if (userDTO.getCart() == null) {
            throw new RuntimeException("Cart ID cannot be null");
        }
        
        Cart cart = cartRepo.findById(userDTO.getCart())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encriptamos la contraseña
        user.setPhoneNumber(userDTO.getPhoneNumber());        
        user.setRole(userDTO.getRole() != null ? Role.valueOf(userDTO.getRole()) : user.getRole());
        user.setCart(cart);
        
        if (userDTO.getOrders() != null && !userDTO.getOrders().isEmpty() ) {      	
            Set<Order> orders = new HashSet<>(orderRepo.findAllById(userDTO.getOrders()));
            
            // Verifica si algunas órdenes no fueron encontradas, puedes manejar este caso según tu lógica
            if (orders.size() != userDTO.getOrders().size()) {
                throw new RuntimeException("Some orders could not be found");
            }
            // Limpiamos las órdenes actuales y agregamos las nuevas
            user.getOrders().clear();  
            user.getOrders().addAll(orders);
        } else {
            // Si orderIds es nulo o vacío, puedes establecer un conjunto vacío
            user.getOrders().clear();
        }
        

        if (userDTO.getReviews() != null && !userDTO.getReviews().isEmpty()) { 
        Set<Review> reviews = new HashSet<>(reviewRepo.findAllById(userDTO.getReviews())); 
        
        // Verifica si algunas reseñas no fueron encontradas, puedes manejar este caso según tu lógica
        if (reviews.size() != userDTO.getReviews().size()) {
            throw new RuntimeException("Some reviews could not be found");
        }
        
        user.getReviews().clear();
        user.getReviews().addAll(reviews);
        }else {
            // Si reviewIds es nulo o vacío, puedes establecer un conjunto vacío
            user.getReviews().clear();

        }
        
        
		return userRepo.save(user);
	}

	public User findUserById(Long id) {
		return userRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("User by id " + id + " was not found"));
		
	}
	
    public User getUserById(Long id) {
    	return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
	
	public User findUserByUsername(String username) {
		return userRepo.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("User by username " + username + " was not found"));

	}

	public void deleteUser(Long id) {
	    User user = userRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));


	    // Primero, eliminamos las relaciones de los carritos con los carros (tabla intermedia cars_carts)
	    if (user.getCart() != null) {
	        Cart cart = user.getCart();
	        
	        // Eliminamos las relaciones en cars_carts
	        for (Car car : cart.getCars()) {
	            car.getCarts().remove(cart);
	        }
	    }

	    // Luego, eliminamos las órdenes del usuario
	    for (Order order : user.getOrders()) {
	        for (Car car : order.getCars()) {
	            car.getOrders().remove(order);
	        }
	        orderRepo.delete(order);
	    }

	    // Eliminamos las reseñas del usuario
	    for (Review review : user.getReviews()) {
	        reviewRepo.delete(review);
	    }

	    // Finalmente, eliminamos el carrito del usuario si existe
	    if (user.getCart() != null) {
	        cartRepo.delete(user.getCart());
	    }

		userRepo.deleteById(id);
	}
}
