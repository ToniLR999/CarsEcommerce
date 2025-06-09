package com.tonilr.CarsEcommerce.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tonilr.CarsEcommerce.Interceptors.LoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);
    
    @Autowired
    private LoggingInterceptor loggingInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("Configurando manejadores de recursos estáticos...");
        
        // Manejador para imágenes locales
        registry.addResourceHandler("/car-images/**")
                .addResourceLocations("classpath:/CarImages/")
                .setCachePeriod(3600)
                .resourceChain(true);
                
        // Manejador para imágenes externas
        registry.addResourceHandler("/external-images/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600)
                .resourceChain(true);
                
        log.info("Manejadores de recursos estáticos configurados");
    }
}
