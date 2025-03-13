package com.tonilr.CarsEcommerce.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.DTOs.OrderDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Entities.Order;
import com.tonilr.CarsEcommerce.Entities.OrderStatus;
import com.tonilr.CarsEcommerce.Entities.Role;
import com.tonilr.CarsEcommerce.Entities.User;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.CarRepo;
import com.tonilr.CarsEcommerce.Repos.OrderRepo;
import com.tonilr.CarsEcommerce.Repos.UserRepo;

import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServices {

	@Autowired
	private final OrderRepo orderRepo;
	
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private CarRepo carRepository;

	public OrderServices(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}

	 @Transactional
	 public Order addOrder(OrderDTO orderDTO) {
	        User user = userRepository.findById(orderDTO.getUserId())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        List<Car> cars = new ArrayList<>(carRepository.findAllById(orderDTO.getcarIds()));
	        if (cars.isEmpty()) {
	            throw new RuntimeException("No cars found for the given IDs");
	        }

	        double totalPrice = cars.stream().mapToDouble(Car::getPrice).sum();

	        Order order = new Order();
	        order.setUser(user);
	        order.setCars(cars);
	        order.setTotalPrice(orderDTO.getTotalPrice() != null ? orderDTO.getTotalPrice() :  totalPrice);
	        order.setStatus(orderDTO.getStatus() != "" ? OrderStatus.valueOf(orderDTO.getStatus()) :  OrderStatus.PENDING);
	        order.setCreatedAt(new Date());

	        return orderRepo.save(order);
	    }

	public List<Order> findAllOrders() {
		return orderRepo.findAll();
	}

	public Order updateOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Car> cars = new ArrayList<>(carRepository.findAllById(orderDTO.getcarIds()));
        if (cars.isEmpty()) {
            throw new RuntimeException("No cars found for the given IDs");
        }
		
        Order order = orderRepo.findById(orderDTO.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setUser(user);
        order.setCars(cars);
        order.setTotalPrice(orderDTO.getTotalPrice() != null ? orderDTO.getTotalPrice() :  order.getTotalPrice());
        order.setStatus(orderDTO.getStatus() != "" ? OrderStatus.valueOf(orderDTO.getStatus()) : order.getStatus());

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
