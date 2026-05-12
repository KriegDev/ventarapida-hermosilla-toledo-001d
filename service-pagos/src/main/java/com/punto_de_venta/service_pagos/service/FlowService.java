package com.punto_de_venta.service_pagos.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punto_de_venta.service_pagos.client.FlowClient;
import com.punto_de_venta.service_pagos.dto.PagoResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FlowService {
    @Autowired
    private FlowClient flowClient;

    public PagoResponse crearPagoFlow(String commerceOrder, String subject, Long amount, String email){
        log.info("Iniciando solicitud a Flow para crear pago. Orden: {}, Monto: {}, commerceOrder, amount");
        try{
            PagoResponse response = flowClient.crearPago(commerceOrder, subject, amount, email);
            log.info("Pago creado exitosamente en Flow. Token recibido: {}");
            return response;
        }catch(Exception e){
            log.error("Error al comunicarse con la API de Flow al crear el pago para la orden {}", commerceOrder, e);
            throw new RuntimeException("Error en la pasarela de pagos al crear la transacción");
        }
    }

    public PagoResponse confirmarPagoFlow(String token){
        log.info("Enviando commit a Flow para el token: {}",token);
        try{
            return flowClient.confirmarPago(token);
        }catch (Exception e){
            log.error("Error al confirmar el pago en Flow con token: {}",token,e);
            throw new RuntimeException("Error confirmado en la transcacción en Flow");
        }
    }

    public PagoResponse consultarEstadoFlow(String token){
       log.info("Consultando estado del pago en Flow para el token: {}",token);
       try{
            return flowClient.consultarEstadoPago(token);
       }catch(Exception e){
            log.error("Error al consultar el estado en Flow para el token: {}",token,e);
            throw new RuntimeException("Error al verificar el estado del pago con Flow");
       }
    }

    public PagoResponse anularPagoFlow(String token){
       log.info("Solicitando enulación en Flow para el token: {}",token);
       try{
            return flowClient.anularPago(token);
       }catch(Exception e){
        log.error("Error al intentar anular el pago en Flow con token: {}",token,e);
        throw new RuntimeException("Error al anular la transacción de Flow");
       }
    }
}
