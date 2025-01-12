package com.tonilr.CarsEcommerce.Services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.Entities.Cart;
import com.tonilr.CarsEcommerce.Entities.Role;
import com.tonilr.CarsEcommerce.Entities.User;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserServices {

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private final UserRepo userRepo;
	
	@Autowired
    private CartServices cartServices;  // Servicio de Carrito

	public UserServices(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	public User addUser(User usuario) {
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());

		usuario.setPassword(encodedPassword);
		usuario.setIsActive(true);
		usuario.setRegisterDate(new Date());
		usuario.setRole(Role.USER);
		 if (usuario.getCart() == null) {
	            Cart newCart = new Cart();
	            newCart = cartServices.addCart(newCart);  // Guardar el carrito en la base de datos
	            usuario.setCart(newCart); // Asignar el carrito al usuario
	            
	            usuario = userRepo.save(usuario);  
	            
	            newCart.setUser(usuario);

	            cartServices.updateCart(newCart);
	        }
		
		//return userRepo.save(usuario);
		 return usuario;
	}
	
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        // Verificar si la contraseña ingresada coincide con la almacenada
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	public User updateUser(User usuario) {
		return userRepo.save(usuario);
	}

	public User findUserById(Long id) {
		return userRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("User by id " + id + " was not found"));

	}
	
	public User findUserByUsername(String username) {
		return userRepo.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("User by username " + username + " was not found"));

	}

	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}
}
