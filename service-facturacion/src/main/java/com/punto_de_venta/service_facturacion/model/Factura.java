package com.punto_de_venta.service_facturacion.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "factura")
@Data @NoArgsConstructor @AllArgsConstructor
public class Factura {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idOrden;

    @Column(nullable = false)
    private Long montoSubtotal;

    @Column(nullable = false)
    private BigDecimal impuesto;

    @Column(nullable = false)
    private Long montoTotal;

    private LocalDateTime fechaEmision;
}
