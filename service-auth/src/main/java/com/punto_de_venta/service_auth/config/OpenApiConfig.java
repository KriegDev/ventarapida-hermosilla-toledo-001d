package com.punto_de_venta.service_auth.config;

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
        return new OpenAPI().info(new Info().title("API punto de venta - Autenticador de usuarios")
        .version("1.0").description("Registro y login de usuarios al sistema de venta"))
        .servers(List.of(new Server().url("http://localhost:4425").description("Autenticador a través del Gateway")));
    }
}
