package com.tonilr.CarsEcommerce.Configurations;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Obtenemos la autenticación actual desde Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Si no hay autenticación o el usuario no está autenticado, devolvemos Optional vacío
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("SYSTEM");  // Default value si no hay usuario autenticado
        }
        System.out.println("Auditor: " + authentication.getName());

        // Retornamos el nombre del usuario autenticado
        return Optional.of(authentication.getName());
    }
}