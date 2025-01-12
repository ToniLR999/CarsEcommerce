package com.tonilr.CarsEcommerce.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tonilr.CarsEcommerce.Entities.User;
import com.tonilr.CarsEcommerce.Services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@Autowired
	private final UserServices userService;
	
	public UserController(UserServices userService) {
		this.userService = userService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.findAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	 @PostMapping("/login")
	  public ResponseEntity<?> loginUser(@RequestBody User user) {
	        User existingUser = userService.findUserByUsername(user.getUsername());
	        if (existingUser != null) {
	            // Verificamos la contraseña con bcrypt
	            boolean passwordMatches = userService.verifyPassword(user.getPassword(), existingUser.getPassword());
	            if (passwordMatches) {
	                // Si la contraseña es correcta, podrías generar un token JWT o enviar una respuesta positiva
	                return ResponseEntity.ok(Map.of("message", "Login successful")); // Envía una respuesta JSON
	            } else {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	        }
	    }

	@GetMapping("/find/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		User user = userService.findUserById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/findByUsername/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
		User user = userService.findUserByUsername(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User newUser = userService.addUser(user);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User updateUser = userService.updateUser(user);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}
	


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
