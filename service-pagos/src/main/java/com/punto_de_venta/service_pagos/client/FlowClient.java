package com.punto_de_venta.service_pagos.client;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.punto_de_venta.service_pagos.config.FlowProperties;
import com.punto_de_venta.service_pagos.dto.PagoResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FlowClient {
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private FlowProperties flowProperties;
    
    public PagoResponse crearPago(String commerceOrder, String subject, Long amount, String email) {
        
        // 1. Validar que las keys existan
        if (flowProperties.getApiKey() == null || flowProperties.getSecretKey() == null) {
            log.error("¡ALERTA! Las credenciales de Flow son nulas. Revisa el application.properties");
            throw new RuntimeException("Credenciales de Flow no configuradas");
        }

        Map<String, String> params = new TreeMap<>();
        // USAMOS TRIM() PARA ELIMINAR ESPACIOS INVISIBLES AL FINAL DE LA CLAVE
        params.put("apiKey", flowProperties.getApiKey().trim());
        params.put("commerceOrder", commerceOrder.trim());
        params.put("subject", subject.trim());
        params.put("currency", "CLP"); 
        params.put("amount", amount.toString());
        params.put("email", email.trim());
        
        // URL obligatorias
        params.put("urlConfirmation", "http://localhost:8080/pago/api/v1/pagos/confirmar-flow"); 
        params.put("urlReturn", "http://localhost:8080/venta/api/v1/ordenes/exito"); 

        // 2. Firmamos los parámetros (Asegurando que la secretKey tampoco tenga espacios)
        String signature = generarFirma(params, flowProperties.getSecretKey().trim());
        params.put("s", signature);

        // 3. Convertimos a Form Data
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.setAll(params);

        log.info("Enviando petición a Flow...");
        
        // 4. Petición a Flow
        return webClientBuilder.build()
                .post()
                .uri(flowProperties.getBaseUrl().trim() + "/payment/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(PagoResponse.class)
                .block();
    }   

    private String generarFirma(Map<String, String> params, String secretKey) {
        try {
            StringBuilder dataToSign = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                dataToSign.append(entry.getKey()).append(entry.getValue());
            }

            // ESTE LOG ES VITAL PARA DEBUGGEAR
            log.info("-> Datos concatenados exactos para la firma: [{}]", dataToSign.toString());

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hash = sha256_HMAC.doFinal(dataToSign.toString().getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            log.info("-> Firma generada: {}", hexString.toString());
            return hexString.toString();

        } catch (Exception e) {
            log.error("Error al generar la firma para Flow", e);
            throw new RuntimeException("Error interno de seguridad al procesar el pago");
        }
    }
    
    // ... mantén el resto de tus métodos (confirmarPago, etc) aquí debajo ...

    public PagoResponse confirmarPago(String token) {
        Map<String, String> params = new TreeMap<>();
        params.put("apiKey", flowProperties.getApiKey());
        params.put("token", token);

        String signature = generarFirma(params, flowProperties.getSecretKey());
        params.put("s", signature);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.setAll(params);

        return webClientBuilder.build()
                .post()
                .uri(flowProperties.getBaseUrl() + "/payment/commit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(PagoResponse.class)
                .block();
    }   

    public PagoResponse consultarEstadoPago(String token) {
        Map<String, String> params = new TreeMap<>();
        params.put("apiKey", flowProperties.getApiKey());
        params.put("token", token);

        String signature = generarFirma(params, flowProperties.getSecretKey());
        
        String urlConParametros = String.format("%s/payment/getStatus?apiKey=%s&token=%s&s=%s", 
                flowProperties.getBaseUrl(), flowProperties.getApiKey(), token, signature);

        return webClientBuilder.build()
                .get()
                .uri(urlConParametros)
                .retrieve()
                .bodyToMono(PagoResponse.class)
                .block();
    }

    public PagoResponse anularPago(String token) {
        Map<String, String> params = new TreeMap<>();
        params.put("apiKey", flowProperties.getApiKey());
        params.put("token", token);
        params.put("amount", "0");

        String signature = generarFirma(params, flowProperties.getSecretKey());
        params.put("s", signature);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.setAll(params);

        return webClientBuilder.build()
                .post()
                .uri(flowProperties.getBaseUrl() + "/payment/refund")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(PagoResponse.class)
                .block();
    }

    
}