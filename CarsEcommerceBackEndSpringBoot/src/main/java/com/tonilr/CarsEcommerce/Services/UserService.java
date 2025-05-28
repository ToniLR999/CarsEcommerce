package com.tonilr.CarsEcommerce.Services;

import com.tonilr.CarsEcommerce.DTOs.CartDTO;
import com.tonilr.CarsEcommerce.DTOs.RegisterRequest;
import com.tonilr.CarsEcommerce.DTOs.UserDTO;
import com.tonilr.CarsEcommerce.Entities.*;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CartRepo cartRepo;
    private final OrderRepo orderRepo;
    private final ReviewRepo reviewRepo;
    private final CartServices cartServices;

    @Autowired
    public UserService(
            UserRepo userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            CartRepo cartRepo,
            OrderRepo orderRepo,
            ReviewRepo reviewRepo,
            CartServices cartServices
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
        this.reviewRepo = reviewRepo;
        this.cartServices = cartServices;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRegisterDate(new Date());
        user.setIsActive(true);
        user.setRole(Role.USER);
        
        return userRepository.save(user);
    }

    @Transactional
    public User createUser(UserDTO userDTO) {
        // Verificar si ya existe un usuario con el mismo username (case-insensitive)
        if (userRepository.existsByUsernameIgnoreCase(userDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRegisterDate(new Date());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setIsActive(true);
        user.setRole(userDTO.getRole() != null ? Role.valueOf(userDTO.getRole()) : Role.USER);

        user = userRepository.save(user);
        
        if (userDTO.getCart() == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart = cartRepo.save(newCart);
            user.setCart(newCart);
            userRepository.save(user);
        } else {
            Cart existentCart = cartServices.findCartById(userDTO.getCart());
            user.setCart(existentCart);
            existentCart.setUser(user);

            CartDTO existingCartDTO = new CartDTO(
                existentCart.getId(), 
                existentCart.getUser().getId(),
                existentCart.getCars().stream().map(Car::getId).collect(Collectors.toList())
            );

            cartServices.updateCartForUser(existingCartDTO);
        }
        
        return userRepository.save(user);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User by id " + id + " was not found"));
    }

    public User findUserByUsername(String username) {
        List<User> users = userRepository.findByUsernameIgnoreCase(username);
        if (users.isEmpty()) {
            throw new RuntimeException("User by username " + username + " was not found");
        }
        if (users.size() > 1) {
            throw new RuntimeException("Multiple users found with username " + username);
        }
        return users.get(0);
    }

    public User updateUser(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            throw new RuntimeException("User ID cannot be null");
        }

        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDTO.getCart() == null) {
            throw new RuntimeException("Cart ID cannot be null");
        }

        Cart cart = cartRepo.findById(userDTO.getCart())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole() != null ? Role.valueOf(userDTO.getRole()) : user.getRole());
        user.setCart(cart);

        // Actualizar órdenes
        if (userDTO.getOrders() != null && !userDTO.getOrders().isEmpty()) {
            Set<Order> orders = new HashSet<>(orderRepo.findAllById(userDTO.getOrders()));
            if (orders.size() != userDTO.getOrders().size()) {
                throw new RuntimeException("Some orders could not be found");
            }
            user.getOrders().clear();
            user.getOrders().addAll(orders);
        } else {
            user.getOrders().clear();
        }

        // Actualizar reseñas
        if (userDTO.getReviews() != null && !userDTO.getReviews().isEmpty()) {
            Set<Review> reviews = new HashSet<>(reviewRepo.findAllById(userDTO.getReviews()));
            if (reviews.size() != userDTO.getReviews().size()) {
                throw new RuntimeException("Some reviews could not be found");
            }
            user.getReviews().clear();
            user.getReviews().addAll(reviews);
        } else {
            user.getReviews().clear();
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getCart() != null) {
            Cart cart = user.getCart();
            for (Car car : cart.getCars()) {
                car.getCarts().remove(cart);
            }
        }

        for (Order order : user.getOrders()) {
            for (Car car : order.getCars()) {
                car.getOrders().remove(order);
            }
            orderRepo.delete(order);
        }

        for (Review review : user.getReviews()) {
            reviewRepo.delete(review);
        }

        if (user.getCart() != null) {
            cartRepo.delete(user.getCart());
        }

        userRepository.deleteById(id);
    }
} 