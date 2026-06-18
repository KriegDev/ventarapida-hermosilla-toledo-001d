package com.punto_de_venta.service_reportes.config;

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
                        .title("API Sistema Punto de Venta - Servicio de Reportes y Analítica")
                        .version("1.0")
                        .description("Documentación del módulo de inteligencia empresarial encargado de la consolidación de ingresos por ventas y conteo de alertas de stock."))
                .servers(List.of(
                        new Server().url("http://localhost:9090").description("Servidor de acceso unificado (API Gateway)")
                ));
    }
}
