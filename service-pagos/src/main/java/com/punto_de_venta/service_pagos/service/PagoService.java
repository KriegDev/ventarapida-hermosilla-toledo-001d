package com.punto_de_venta.service_pagos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punto_de_venta.service_pagos.model.MetodoPago;
import com.punto_de_venta.service_pagos.model.Pago;
import com.punto_de_venta.service_pagos.dto.PagoDTO;
import com.punto_de_venta.service_pagos.dto.PagoResponse;
import com.punto_de_venta.service_pagos.repository.MetodoPagoRepository;
import com.punto_de_venta.service_pagos.repository.PagoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private FlowService flowService;

    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    public Pago buscarPagoId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El pago no existe"));
    }

    
@Transactional
public Pago procesarPago(PagoDTO dto) { 
    log.info("Iniciando procesamiento de pago para la Orden ID: {}", dto.getIdOrden());

    if (dto.getIdMetPago()==null) {
        throw new RuntimeException("ID llegó nulo. DTO: "+dto.toString());
    }
   
    MetodoPago metodo = metodoPagoRepository.findById(dto.getIdMetPago())
            .orElseThrow(() -> new RuntimeException("Error: Método de pago no encontrado."));
    
    
    Pago pago = new Pago();
    pago.setIdOrden(dto.getIdOrden());
    pago.setMonto(dto.getMonto());
    pago.setMetodoPago(metodo); 
    pago.setFechaCreacion(LocalDateTime.now());
    
    if (metodo.getId() == 4L) {
            log.info("Método Flow detectado. Generando link de pago...");
            
            pago.setEstado("PENDIENTE");
            pago.setEstadoFlow("PENDIENTE_FLOW");

            PagoResponse response = flowService.crearPagoFlow(
                pago.getIdOrden().toString(),
                "Pago POS - Orden #" + pago.getIdOrden(),
                pago.getMonto(),
                "carolinafk4@gmail.com" 
            );
            pago.setFlowToken(response.getToken());
            
            String urlDePago = response.getUrl() + "?token=" + response.getToken();
            pago.setDatosOrden(urlDePago);

        } else {
            log.info("Método local detectado (ID: {}). Aprobando pago directamente.", metodo.getId());
            //Casos locales: Efectivo, Tarjeta física en terminal (1, 2, 3)
            pago.setEstado("PAGADO");
            pago.setEstadoFlow("N/A");
            pago.setFechaPago(LocalDateTime.now());
        }
        return pagoRepository.save(pago);
    }

    @Transactional
    public Pago confirmarPagoFlow(String token) {
        log.info("Confirmando pago en BD para el token: {}", token);

        Pago pago = pagoRepository.findByFlowToken(token)
                .orElseThrow(() -> new RuntimeException("No existe un pago asociado a este token de Flow"));

        PagoResponse estadoReal = flowService.consultarEstadoFlow(token);

        // Flow status: 1:Pendiente, 2: Pagado, 3: Rechazado, 4: Anulado
        if (estadoReal.getStatus() != null && estadoReal.getStatus() == 2) {
            pago.setEstado("PAGADO");
            pago.setEstadoFlow("PAID");
            pago.setFechaPago(LocalDateTime.now());
            pago.setFlowOrder(estadoReal.getFlowOrder() != null ? estadoReal.getFlowOrder().toString() : "N/A");
            log.info("Pago confirmado exitosamente. Orden ID: {}", pago.getIdOrden());
        } else {
            pago.setEstado("RECHAZADO");
            pago.setEstadoFlow("REJECTED");
            log.warn("El pago para la Orden ID {} no fue exitoso en Flow. Status: {}", pago.getIdOrden(), estadoReal.getStatus());
        }
        pago.setFlowToken(token);
        return pagoRepository.save(pago);
    }

    @Transactional
    public Pago anularPago(Long id) {
        log.info("Solicitud de anulación para el Pago ID: {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        

        if (pago.getFlowToken() != null && pago.getEstado().equals("PAGADO")) {
            flowService.anularPagoFlow(pago.getFlowToken());
            log.info("Anulación enviada a Flow para el token: {}", pago.getFlowToken());
        }
        pago.setEstado("ANULADO");
        pago.setEstadoFlow("CANCELED");
        return pagoRepository.save(pago);
    }
}