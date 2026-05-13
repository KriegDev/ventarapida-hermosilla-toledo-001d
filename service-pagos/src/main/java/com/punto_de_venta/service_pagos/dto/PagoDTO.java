package com.punto_de_venta.service_pagos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {
    private Long idOrden;
    private Long monto;
    @JsonProperty("idMetodoPago")
    private Long idMetPago;
}
