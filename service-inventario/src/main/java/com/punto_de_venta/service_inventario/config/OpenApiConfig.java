package com.punto_de_venta.service_inventario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema Punto de Venta - Servicio de Inventario")
                        .version("1.0")
                        .description("Documentación del núcleo de control de existencias, umbrales mínimos de stock e historial detallado de movimientos de almacén."))
                .servers(List.of(
                        new Server().url("http://localhost:9090").description("Servidor de acceso unificado (API Gateway)")
                ));
    }
}
