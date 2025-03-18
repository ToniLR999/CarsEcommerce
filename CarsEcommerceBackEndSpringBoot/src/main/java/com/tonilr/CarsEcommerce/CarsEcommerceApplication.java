package com.tonilr.CarsEcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarsEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarsEcommerceApplication.class, args);
	}

}
