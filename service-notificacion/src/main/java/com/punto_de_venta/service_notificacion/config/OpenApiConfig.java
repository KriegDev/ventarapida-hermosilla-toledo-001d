package com.punto_de_venta.service_notificacion.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI().info(new Info().title("API punto de venta - Servicio de notificaciones")
        .version("1.0").description("Gestión y visualización completa de notificaciones del sistema"))
        .servers(List.of(new Server().url("http://localhost:4425").description("Documentación centralizada de notificaciones,ingreso a través del Gateway")))
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
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