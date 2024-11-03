package com.tonilr.CarsEcommerce.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.Entities.Order;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.OrderRepo;

@Service
public class OrderServices {

	@Autowired
	private final OrderRepo orderRepo;

	public OrderServices(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}

	public Order addOrder(Order order) {
		
	order.setCreatedAt(LocalDateTime.now());

		return orderRepo.save(order);
	}

	public List<Order> findAllOrders() {
		return orderRepo.findAll();
	}

	public Order updateOrder(Order order) {
		return orderRepo.save(order);
	}

	public Order findOrderById(Long id) {
		return orderRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Order by id " + id + " was not found"));

	}

	public void deleteOrder(Long id) {
		orderRepo.deleteById(id);
	}
	
}
