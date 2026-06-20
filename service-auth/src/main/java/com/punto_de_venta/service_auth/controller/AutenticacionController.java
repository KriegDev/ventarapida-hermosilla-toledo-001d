package com.punto_de_venta.service_auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_auth.dto.AuthRequest;
import com.punto_de_venta.service_auth.model.Usuario;
import com.punto_de_venta.service_auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autentication", description = "Endpoints para registro y login de usuarios.")
public class AutenticacionController {
    @Autowired
    private AuthService authService;
    @Operation(summary = "Registrar un nuevo usuario", description = "Guarda el usuario con la contraseña encriptada")
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody Usuario usuario){
        return ResponseEntity.ok(authService.registrar(usuario));
    }
    @Operation(summary = "Iniciar sesión", description = "Retorna el Token JWT si las credenciales son válidas")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request){
        try {
            String token = authService.login(request.getNombreUsuario(), request.getContrasena());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
