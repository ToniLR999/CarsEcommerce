package com.tonilr.CarsEcommerce.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AppConfig implements WebMvcConfigurer{

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Apply to all endpoints
                .allowedOrigins("http://localhost:4200")  // Allow Angular app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allowed HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true);  // Allow sending cookies (if necessary)
    }
}
