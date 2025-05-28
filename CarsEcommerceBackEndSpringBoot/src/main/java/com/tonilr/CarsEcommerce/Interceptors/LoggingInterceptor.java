package com.tonilr.CarsEcommerce.Interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("=== Request Debug Info ===");
        logger.info("URL: {}", request.getRequestURL());
        logger.info("Method: {}", request.getMethod());
        logger.info("Remote Address: {}", request.getRemoteAddr());
        logger.info("Headers: {}", Collections.list(request.getHeaderNames())
            .stream()
            .collect(Collectors.toMap(
                headerName -> headerName,
                request::getHeader
            )));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info("=== Response Debug Info ===");
        logger.info("Status: {}", response.getStatus());
        if (ex != null) {
            logger.error("Error occurred: ", ex);
        }
    }
}
