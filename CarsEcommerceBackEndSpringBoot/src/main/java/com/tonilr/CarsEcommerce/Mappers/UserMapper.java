package com.tonilr.CarsEcommerce.Mappers;

import com.tonilr.CarsEcommerce.DTOs.UserDTO;
import com.tonilr.CarsEcommerce.Entities.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    // Método estático para mapear User a UserDTO
    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;  // En caso de que el user sea null, devuelve null
        }

        // Convertir roles a String, asumiendo que Role tiene un método 'toString()'
        String role = user.getRole() != null ? user.getRole().toString() : null;

        // Mapear las relaciones de sets (orders, reviews) a Set<Long> para usar en el DTO
        Set<Long> orderIds = user.getOrders().stream()
                .map(order -> order.getId())  // Suponiendo que 'Order' tiene un método getId()
                .collect(Collectors.toSet());

        Set<Long> reviewIds = user.getReviews().stream()
                .map(review -> review.getId())  // Suponiendo que 'Review' tiene un método getId()
                .collect(Collectors.toSet());

        // Obtener el cartId
        Long cartId = user.getCart() != null ? user.getCart().getId() : null;

        // Crear y devolver el UserDTO
        return new UserDTO(
            user.getId(), 
            user.getUsername(), 
            user.getEmail(), 
            user.getRegisterDate(),
            user.getPhoneNumber(),
            user.getIsActive(),
            role,
            orderIds,
            reviewIds,
            cartId
        );
    }
    
    // Método para convertir una lista de Users en una lista de UserDTOs
    public static List<UserDTO> toUserDTO(List<User> users) {
        return users.stream()
                    .map(UserMapper::toUserDTO)  // Usamos el método anterior para cada User
                    .collect(Collectors.toList());  // Convertimos el stream a una lista
    }
}
