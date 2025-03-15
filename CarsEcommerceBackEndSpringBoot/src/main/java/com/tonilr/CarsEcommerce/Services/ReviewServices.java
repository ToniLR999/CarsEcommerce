package com.tonilr.CarsEcommerce.Services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tonilr.CarsEcommerce.DTOs.ReviewDTO;
import com.tonilr.CarsEcommerce.Entities.Car;
import com.tonilr.CarsEcommerce.Entities.Review;
import com.tonilr.CarsEcommerce.Entities.User;
import com.tonilr.CarsEcommerce.Exceptions.NotFoundException;
import com.tonilr.CarsEcommerce.Repos.CarRepo;
import com.tonilr.CarsEcommerce.Repos.ReviewRepo;
import com.tonilr.CarsEcommerce.Repos.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class ReviewServices {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private CarRepo carRepository;

	public ReviewServices(ReviewRepo reviewRepo) {
		this.reviewRepo = reviewRepo;
	}
	
	   @Transactional
	    public Review addReview(ReviewDTO reviewDTO) {
	        User user = userRepository.findById(reviewDTO.getUserId())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        Car car = carRepository.findById(reviewDTO.getCarId())
	                .orElseThrow(() -> new RuntimeException("Car not found"));

	        Review review = new Review();
	        review.setUser(user);
	        review.setCar(car);
	        review.setRating(reviewDTO.getRating());
	        review.setComment(reviewDTO.getComment());
	        review.setCreatedAt(new Date());
	        
	        //updatear foreign keys
	        user.addReview(review);
	        car.addReview(review);// Método que añade el order a la colección 'orders' del user y establece la relación bidireccional

	        return reviewRepo.save(review);
	    }

	public List<Review> findAllReviews() {
		return reviewRepo.findAll();
	}

	public Review updateReview(ReviewDTO reviewDTO) {
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Car car = carRepository.findById(reviewDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
		
        Review review = reviewRepo.findById(reviewDTO.getId())
                .orElseThrow(() -> new RuntimeException("Review not found"));
        
        review.setUser(user);
        review.setCar(car);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        
        user.addReview(review);
        car.addReview(review);

		return reviewRepo.save(review);
	}

	public Review findReviewById(Long id) {
		return reviewRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Review by id " + id + " was not found"));

	}

	public void deleteReview(Long id) {
		  Review review = reviewRepo.findById(id)
		            .orElseThrow(() -> new RuntimeException("Review not found"));
		    
		    // Desvincular la review del coche
		    // Desvincula la review de car y user
		    review.getCar().getReviews().remove(review);
		    review.getUser().getReviews().remove(review);

		    carRepository.save(review.getCar());
		    userRepository.save(review.getUser());
		
		
		reviewRepo.deleteById(id);
	}
}
