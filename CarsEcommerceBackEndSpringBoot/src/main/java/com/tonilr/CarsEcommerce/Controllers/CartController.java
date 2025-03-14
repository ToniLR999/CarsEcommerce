package com.tonilr.CarsEcommerce.Controllers;

import java.util.List;
import java.util.Optional;

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

import com.tonilr.CarsEcommerce.DTOs.CartDTO;
import com.tonilr.CarsEcommerce.Entities.Cart;
import com.tonilr.CarsEcommerce.Entities.User;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.UserRepo;
import com.tonilr.CarsEcommerce.Services.CartServices;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private final CartServices cartService;
	
	@Autowired
	private UserRepo userRepository;

	
	public CartController(CartServices cartService) {
		this.cartService = cartService;

	}

	@GetMapping("/all")
	public ResponseEntity<List<Cart>> getAllCarts() {
		List<Cart> carts = cartService.findAllCarts();
		return new ResponseEntity<>(carts, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Cart> getCartById(@PathVariable("id") Long id) {
		Cart cart = cartService.findCartById(id);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Cart> addCart(@RequestBody CartDTO cartDTO) {

		Cart newCart = cartService.addCart(cartDTO);
		return new ResponseEntity<>(newCart, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
    public ResponseEntity<Cart> updateCart(@RequestBody CartDTO cartDTO) {
		System.out.println("Cart recibido: "+cartDTO.getId() + " - "+cartDTO.getUser()+ " - "+cartDTO.getCars());
        Cart updatedCart = cartService.updateCart(cartDTO);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable("id") Long id) {
		cartService.deleteCart(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
