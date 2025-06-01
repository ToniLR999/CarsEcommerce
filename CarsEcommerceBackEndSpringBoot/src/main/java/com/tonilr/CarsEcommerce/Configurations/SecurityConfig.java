package com.tonilr.CarsEcommerce.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(
        AuthenticationProvider authenticationProvider,
        JwtAuthenticationFilter jwtAuthFilter
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Configurando SecurityFilterChain...");
        
        http
            .csrf(csrf -> {
                csrf.disable();
                log.info("CSRF deshabilitado");
            })
            .cors(cors -> {
                cors.configurationSource(corsConfigurationSource());
                log.info("CORS configurado");
            })
            .authorizeHttpRequests(auth -> {
                log.info("Configurando autorizaciones...");
                auth
                    .requestMatchers("/car/**").permitAll()
                    .requestMatchers("/category/**").permitAll()
                    .requestMatchers("/user/login").permitAll()
                    .requestMatchers("/user/register").permitAll()
                    .requestMatchers("/user/all").permitAll()
                    .anyRequest().authenticated();
                log.info("Autorizaciones configuradas");
            })
            .sessionManagement(session -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                log.info("Política de sesión configurada como STATELESS");
            });
        
        log.info("SecurityFilterChain configurado exitosamente");
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Configurando CORS...");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        log.info("CORS configurado exitosamente");
        return source;
    }
}
