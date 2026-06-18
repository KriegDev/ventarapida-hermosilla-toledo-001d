package com.punto_de_venta.service_inventario.model;


import java.math.BigDecimal;
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
@Table(name = "movimiento_inventario")
@Data @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Clase que representa los movimientos de inventario")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único autoincremental", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    @Schema(description = "datos del stock que se movió")
    private Stock stock;

    @NotBlank(message = "No puede contener espacios en blancos ni nulos")
    @Column(name = "tipo_movimiento",  nullable = false) 
    @Schema(description = "descripción del tipo de movimiento", example = "ENTRADA/SALIDA")
    private String tipoMovimiento;

    @NotNull(message = "No puede contener espacios en blancos ni nulos")
    @Min(value = 0, message = "El monto no puede ser menor a cero")
    @Column(nullable = false)
    @Schema(description = "Cantidad de unidades que se moverán del stock", example = "2.0")
    private BigDecimal monto;

    @Column(name = "fecha_movimiento",  nullable = false)
    @Schema(description = "Registro de la fecha donde se realiza el movimiento")
    private LocalDateTime fechaMovimiento;
}
