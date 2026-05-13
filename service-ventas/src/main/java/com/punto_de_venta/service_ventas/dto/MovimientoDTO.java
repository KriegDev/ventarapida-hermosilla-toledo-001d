package com.punto_de_venta.service_ventas.dto;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {
    private String tipoMovimiento;
    private BigDecimal monto;
    private LocalDateTime fechaMovimiento;

    public MovimientoDTO(String tipoMovimiento, Long cantidad) {
        this.tipoMovimiento = tipoMovimiento;
        this.monto = BigDecimal.valueOf(cantidad);
        this.fechaMovimiento = LocalDateTime.now();
    }
}
