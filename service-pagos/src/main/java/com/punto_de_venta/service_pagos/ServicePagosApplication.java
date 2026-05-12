package com.punto_de_venta.service_pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.punto_de_venta.service_pagos.config.FlowProperties;

@SpringBootApplication

@EnableConfigurationProperties(FlowProperties.class)
public class ServicePagosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePagosApplication.class, args);
	}

}
