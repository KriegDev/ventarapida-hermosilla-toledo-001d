package com.punto_de_venta.service_facturacion.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema Punto de Venta - Servicio de Facturación\"")
                        .version("1.0")
                        .description("Documentación de operaciones para emisión, consulta e historial cronológico de facturas y recibos comerciales."))
                .servers(List.of(
                    new Server().url("http://localhost:4425").description("Servidor de acceso unificado (API Gateway)")
                ));
    }
}
