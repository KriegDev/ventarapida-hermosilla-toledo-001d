package com.punto_de_venta.service_ventas.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema Punto de Venta - Servicio de Ventas")
                        .version("1.0")
                        .description("Documentación central del motor de transacciones, procesamiento de órdenes, cálculo de montos totales y estados de venta."))
                .servers(List.of(
                        new Server().url("http://localhost:4425").description("Servidor de acceso unificado (API Gateway)"))).addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                    .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
                );
    }
}
