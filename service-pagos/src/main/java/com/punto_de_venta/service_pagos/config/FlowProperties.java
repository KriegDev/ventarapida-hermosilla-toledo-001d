package com.punto_de_venta.service_pagos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "flow")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowProperties {
    
    private String apiKey;
    private String secretKey;
    private String baseUrl;
}
