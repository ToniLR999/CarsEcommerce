package com.tonilr.CarsEcommerce.Controllers;

import com.tonilr.CarsEcommerce.Services.JwtService;
import com.tonilr.CarsEcommerce.Services.UserSessionService;
import com.tonilr.CarsEcommerce.DTOs.AuthRequest;
import com.tonilr.CarsEcommerce.DTOs.AuthResponse;
import com.tonilr.CarsEcommerce.DTOs.RegisterRequest;
import com.tonilr.CarsEcommerce.Entities.User;
import com.tonilr.CarsEcommerce.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserSessionService userSessionService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtService jwtService,
            UserSessionService userSessionService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.userSessionService = userSessionService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        String token = jwtService.generateToken(user);
        userSessionService.saveUserSession(token, user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        
        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        userSessionService.saveUserSession(token, (User) userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            userSessionService.removeUserSession(jwt);
        }
        return ResponseEntity.ok().build();
    }
} 