package com.tonilr.CarsEcommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.Entities.Cart;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.CartRepo;

@Service
public class CartServices {

	@Autowired
	private final CartRepo cartRepo;

	public CartServices(CartRepo cartRepo) {
		this.cartRepo = cartRepo;
	}

	public Cart addCart(Cart cart) {
		return cartRepo.save(cart);
	}

	public List<Cart> findAllCarts() {
		return cartRepo.findAll();
	}

	public Cart updateCart(Cart cart) {
		return cartRepo.save(cart);
	}

	public Cart findCartById(Long id) {
		return cartRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Cart by id " + id + " was not found"));

	}

	public void deleteCart(Long id) {
		cartRepo.deleteById(id);
	}
}
