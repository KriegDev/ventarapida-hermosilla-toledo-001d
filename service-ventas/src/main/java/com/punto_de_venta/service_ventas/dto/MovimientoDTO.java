package com.punto_de_venta.service_ventas.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {
    private String tipoMovimiento;
    private BigDecimal monto;
}
