package com.punto_de_venta.service_pagos.model;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;

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
    private Long id;
    
    @NotNull(message = "No puede tener nulos ni espacios")
    @Column (name="id_orden", nullable = false)
    private Long idOrden;

    @Transient
    private Object datosOrden;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Min(value = 0, message = "No debe ser menor a 0")
    @NotNull(message = "No puede contener espacios vacios")
    @Column(nullable = false)
    private Long monto;

    @NotBlank(message = "No puede tener nulos ni espacios vacios")
    @Column(nullable = false)
    private String estado;

    
    @Column(name="flow_token")
    private String flowToken;

    
    @Column(name="flow_order")
    private String flowOrder;

    @NotBlank(message = "No puede tener nulos ni espacios vacios")
    @Column(name="estado_flow",nullable = false)
    private String estadoFlow;
    
   
    @Column(name="fecha_pago")
    private LocalDateTime fechaPago;

    @NotNull(message = "No puede tener nulos ni espacios vacios")
    @Column(name="fecha_creacion",nullable = false)
    private LocalDateTime fechaCreacion;
}
