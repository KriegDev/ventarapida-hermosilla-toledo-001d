package com.punto_de_venta.service_pagos.model;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del pago", example = "1")
    private Long id;
    
    @NotNull(message = "No puede tener nulos ni espacios")
    @Column (name="id_orden", nullable = false)
    @Schema(description = "ID de la orden asociada al pago", example = "10")
    private Long idOrden;

    @Transient
    @Schema(description = "Datos de la orden obtenidos desde otro microservicio")
    private Object datosOrden;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    @Schema(description = "Método de pago asociado al pago")
    private MetodoPago metodoPago;

    @Min(value = 0, message = "No debe ser menor a 0")
    @NotNull(message = "No puede contener espacios vacios")
    @Column(nullable = false)
    @Schema(description = "Monto total del pago", example = "15000") 
    private Long monto;

    @NotBlank(message = "No puede tener nulos ni espacios vacios")
    @Column(nullable = false)
    @Schema(description = "Estado interno del pago", example = "PENDIENTE")
    private String estado;

    
    @Column(name="flow_token")
    @Schema(description = "Token generado por Flow para procesar el pago", example = "abc123token")
    private String flowToken;

    
    @Column(name="flow_order")
    @Schema(description = "Número de orden generado por Flow", example = "123456")
    private String flowOrder;

    @NotBlank(message = "No puede tener nulos ni espacios vacios")
    @Column(name="estado_flow",nullable = false)
    @Schema(description = "Estado del pago informado por Flow", example = "PENDIENTE")
    private String estadoFlow;
    
   
    @Column(name="fecha_pago")
    @Schema(description = "Fecha en que el pago fue confirmado")
    private LocalDateTime fechaPago;

    @NotNull(message = "No puede tener nulos ni espacios vacios")
    @Column(name="fecha_creacion",nullable = false)
    @Schema(description = "Fecha en que se creó el registro del pago")
    private LocalDateTime fechaCreacion;
}
