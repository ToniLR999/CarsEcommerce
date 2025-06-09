package com.tonilr.CarsEcommerce.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/external-images")
@CrossOrigin(origins = "http://localhost:4200")
public class ExternalImageController {
    
    private static final Logger logger = LoggerFactory.getLogger(ExternalImageController.class);
    
    @GetMapping("/**")
    public ResponseEntity<byte[]> getExternalImage(HttpServletRequest request) {
        String imageUrl = request.getRequestURI().replace("/external-images/", "");
        logger.info("URL original recibida: {}", imageUrl);
        
        try {
            // Decodificar la URL
            imageUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.toString());
            
            // Asegurarse de que la URL tenga el protocolo correcto
            if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
                imageUrl = "https://" + imageUrl;
            }
            
            // Corregir doble slash si existe
            imageUrl = imageUrl.replace("https:/", "https://");
            
            logger.info("URL procesada: {}", imageUrl);
            
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                byte[] imageBytes = connection.getInputStream().readAllBytes();
                String contentType = connection.getContentType();
                
                logger.info("Imagen obtenida exitosamente. Tipo: {}", contentType);
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);
            } else {
                logger.error("Error al obtener la imagen. Código de respuesta: {}", connection.getResponseCode());
                return ResponseEntity.status(connection.getResponseCode()).build();
            }
        } catch (IOException e) {
            logger.error("Error al procesar la imagen externa: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
