package com.tonilr.CarsEcommerce.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tonilr.CarsEcommerce.DTOs.UserDTO;
import com.tonilr.CarsEcommerce.Entities.User;
import com.tonilr.CarsEcommerce.Mappers.UserMapper;
import com.tonilr.CarsEcommerce.Services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
		String username = loginRequest.get("username");
		String password = loginRequest.get("password");
	
		if (username == null || password == null) {
			return ResponseEntity.badRequest().body("Username and password are required");
		}
	
		User existingUser = userService.findUserByUsername(username);
		if (existingUser != null) {
			boolean passwordMatches = userService.verifyPassword(password, existingUser.getPassword());
			if (passwordMatches) {
				return ResponseEntity.ok(Map.of("message", "Login successful"));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
	    User user = userService.findUserById(id);
	    // Convertimos el User a UserDTO usando el mapeador
	    UserDTO userDTO = UserMapper.toUserDTO(user);
	    
	    // Retornamos el UserDTO envuelto en un ResponseEntity con código de estado 200 (OK)
	    return ResponseEntity.ok(userDTO);	}
	
	@GetMapping("/findByUsername/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
		User user = userService.findUserByUsername(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) {
		User updateUser = userService.updateUser(userDTO);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}
	


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
