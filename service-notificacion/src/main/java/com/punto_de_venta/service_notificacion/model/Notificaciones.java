package com.punto_de_venta.service_notificacion.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notificaciones")
@Schema(description = "Modelo utilizado para representar las notificaciones del sistema de venta.")
public class Notificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental", example = "1")
    private Long id;
    @Email(message = "Formato de correo inválido, intente nuevamente")
    @Column(nullable = false)
    @NotBlank(message = "Debe ingresar un correo")
    @Schema(description = "Correo con formato correcto", example = "cachupin15@gmail.com")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "Debe ingresar un tipo de notificación")
    @Schema(description = "Tipo de notificación", example = "Compra registrada")
    private String tipo;
    @Column(nullable = false)
    @NotBlank(message = "Debe ingresar el estado de la notificación")
    @Schema(description = "Estado de la notificación", example = "Enviada")
    private String status;
    @Column(name = "fecha_envio", nullable = false)
    @NotNull(message = "Debe ingresar fecha de envío")
    @Schema(description = "Fecha de envío", example = "02-07-2026")
    private LocalDate fechaEnvio;

}
