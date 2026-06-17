package com.punto_de_venta.service_proveedores.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class MovimientoInventarioDTO {
    

    private BigDecimal monto;

    private String tipoMovimiento;

}
