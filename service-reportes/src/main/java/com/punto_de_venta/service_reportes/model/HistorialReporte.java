package com.punto_de_venta.service_reportes.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa el registro histórico de un reporte generado, consolidando ventas y alertas de stock")
public class HistorialReporte {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del reporte en el historial", example = "1")
    private Long id;
    
    @Column(nullable = false)
    @Schema(description = "Clasificación o categoría del reporte generado (ej. DIARIO, SEMANAL, AUDITORIA)", example = "DIARIO")
    private String tipoReporte;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora exacta en la que se ejecutó y consolidó el reporte", example = "2026-06-18T12:30:00")
    private LocalDateTime fechaGeneracion;

    @Column(nullable = false)
    @Schema(description = "Suma total del monto de las ventas procesadas durante el periodo del reporte", example = "450000")
    private Long totalVentasCalculadas;

    @Column(nullable = false)
    @Schema(description = "Cantidad de productos detectados que se encuentran por debajo de su umbral de stock mínimo", example = "12")
    private Long alertasStockDetectadas;

    @Schema(description = "Identificador del usuario o proceso automatizado que detonó la creación del reporte", example = "SYSTEM")
    private String generadoPor = "SYSTEM";
}
