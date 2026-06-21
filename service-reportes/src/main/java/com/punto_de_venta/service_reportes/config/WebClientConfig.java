package com.punto_de_venta.service_reportes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder(ServiceTokenProvider serviceTokenProvider) {
        return WebClient.builder()
                .filter((request, next) -> {
                    ClientRequest newRequest = ClientRequest
                            .from(request)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + serviceTokenProvider.getToken())
                            .build();

                    return next.exchange(newRequest);
                });
    }
}