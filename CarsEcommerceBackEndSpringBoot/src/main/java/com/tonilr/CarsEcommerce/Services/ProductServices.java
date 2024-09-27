package com.tonilr.CarsEcommerce.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.Entities.Product;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.ProductRepo;

@Service
public class ProductServices {

	@Autowired
	private final ProductRepo productRepo;

	public ProductServices(ProductRepo productRepo) {
		this.productRepo = productRepo;
	}

	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}

	public Product updateProduct(Product product) {
		return productRepo.save(product);
	}

	public Product findProductById(Long id) {
		return productRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Product by id " + id + " was not found"));

	}

	public void deleteProduct(Long id) {
		productRepo.deleteById(id);
	}
}
