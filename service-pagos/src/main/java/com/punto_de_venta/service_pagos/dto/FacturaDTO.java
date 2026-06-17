package com.punto_de_venta.service_pagos.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FacturaDTO {

    private Long idOrden;
    private Long montoTotal;
}
