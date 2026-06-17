package com.punto_de_venta.service_reportes.model;

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
@Table(name = "historial_reportes")
@Data @AllArgsConstructor @NoArgsConstructor
public class HistorialReporte {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tipoReporte;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;

    @Column(nullable = false)
    private Long totalVentasCalculadas;

    @Column(nullable = false)
    private Long alertasStockDetectadas;

    private String generadoPor = "SYSTEM";
}
