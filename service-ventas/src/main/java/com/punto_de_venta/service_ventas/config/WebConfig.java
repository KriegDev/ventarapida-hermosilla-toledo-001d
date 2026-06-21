package com.punto_de_venta.service_ventas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.HttpHeaders;



@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }


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
