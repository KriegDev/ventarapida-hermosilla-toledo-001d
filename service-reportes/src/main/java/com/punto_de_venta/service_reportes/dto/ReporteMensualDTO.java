package com.punto_de_venta.service_reportes.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ReporteMensualDTO {

    private String mes;
    private Long totalVentas;
    private BigDecimal montoTotalRecaudado;
    private Long cantidadProductosBajoStock;
}
