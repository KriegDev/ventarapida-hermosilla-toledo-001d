package com.punto_de_venta.service_auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroRequest {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 15, message = "El nombre debe tener entre 3 y 15 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, max = 15, message = "La contraseña debe tener entre 8 y 15 caracteres")
    private String contrasena;
}