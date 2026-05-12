package com.punto_de_venta.service_pagos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagoResponse {

    private String token;
    private String url;
    private Integer status;
    private Long flowOrder;
    private String commerceOrder;
    private Long amount;
}
