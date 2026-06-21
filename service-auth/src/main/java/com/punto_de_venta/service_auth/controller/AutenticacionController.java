package com.punto_de_venta.service_auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_auth.dto.AuthRequest;
import com.punto_de_venta.service_auth.dto.RegistroRequest;
import com.punto_de_venta.service_auth.model.Usuario;
import com.punto_de_venta.service_auth.service.AuthService;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Autentication", description = "Endpoints para registro y login de usuarios.")
@CrossOrigin(origins = "*")
public class AutenticacionController {
    @Autowired
    private AuthService authService;
    @Operation(summary = "Registrar un nuevo usuario", description = "Guarda el usuario con la contraseña encriptada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incorrectos.")
    })
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@Valid @RequestBody RegistroRequest request){
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setContrasena(request.getContrasena()); 
        return ResponseEntity.ok(authService.registrar(usuario));
    }

    @Operation(summary = "Iniciar sesión", description = "Retorna el Token JWT si las credenciales son válidas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario existente, copie token para autorizar su ingreso."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incorrectos."),
        @ApiResponse(responseCode = "404", description = "Usuario no registrado en el sistema.")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest request){
        try {
            String token = authService.login(request.getNombreUsuario(), request.getContrasena());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
