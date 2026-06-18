package com.punto_de_venta.service_pagos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "metodo_pago")
public class MetodoPago {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del método de pago", example = "1")
    private Long id;

    @NotBlank(message = "No puede tener nulos ni espacios en blanco")
    @Size(max = 25,message = "El máximo es de 25")
    @Column(nullable=false, length = 25, unique = true)
    @Schema(description = "Nombre del método de pago", example = "Transferencia")
    private String nombre;

    @NotNull(message = "No puede contener espacios en blancos ni nulos")
    @Column(nullable = false)
     @Schema(description = "Indica si el método de pago está activo", example = "true")
    private Boolean activo;
}
