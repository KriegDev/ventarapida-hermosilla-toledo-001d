package com.punto_de_venta.service_notificacion.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notificaciones")
public class Notificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "Formato de correo inválido, intente nuevamente")
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String tipo;
    @Column(nullable = false)
    private String status;
    @Column(name = "fecha_envio", nullable = false)
    private LocalDate fechaEnvio;

}
