package mx.edu.tecdesoftware.Astrit_restaurant_backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.dto.LoginRequest;
import mx.edu.tecdesoftware.Astrit_restaurant_backend.domain.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Autentica al cliente y devuelve un token JWT")
    @ApiResponse(responseCode = "200", description = "Autenticación exitosa, retorna el JWT")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        // Delegamos toda la validación a nuestro servicio de dominio
        String jwtToken = authService.login(request.getUsername(), request.getPassword());

        if (jwtToken != null) {
            return ResponseEntity.ok(jwtToken);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}