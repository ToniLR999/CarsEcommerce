package com.tonilr.CarsEcommerce.Interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;
import java.util.stream.Collectors;


@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/car-images/")) {
            log.info("Petición de imagen recibida: {}", requestURI);
            log.info("Headers: {}", Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                    headerName -> headerName,
                    request::getHeader
                )));
        }
        log.info("Petición recibida: {} {} desde {}", 
            request.getMethod(), 
            request.getRequestURI(),
            request.getRemoteAddr());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("Respuesta enviada: {} {} - Status: {}", 
            request.getMethod(), 
            request.getRequestURI(),
            response.getStatus());
    }
}
