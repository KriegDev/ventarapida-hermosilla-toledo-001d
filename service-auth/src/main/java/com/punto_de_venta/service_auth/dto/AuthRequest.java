package com.punto_de_venta.service_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String nombreUsuario;
    private String contrasena;
}
