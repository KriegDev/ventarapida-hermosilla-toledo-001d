package com.punto_de_venta.service_ventas.config;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ServiceTokenProvider {

    @Value("${internal.auth.login-url}")
    private String loginUrl;

    @Value("${internal.auth.username}")
    private String username;

    @Value("${internal.auth.password}")
    private String password;

    private String token;
    private LocalDateTime tokenExpiration = LocalDateTime.now();

    public String getToken() {
        if (token == null || LocalDateTime.now().isAfter(tokenExpiration)) {
            token = login();
            tokenExpiration = LocalDateTime.now().plusMinutes(110);
        }

        return token;
    }

    private String login() {
        Map<String, String> body = Map.of(
                "nombreUsuario", username,
                "contrasena", password
        );

        return WebClient.create()
                .post()
                .uri(loginUrl)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
