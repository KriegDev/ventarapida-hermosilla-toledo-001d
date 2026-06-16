package com.punto_de_venta.service_pagos.controller;

import com.punto_de_venta.service_pagos.model.MetodoPago;
import com.punto_de_venta.service_pagos.service.MetodoPagoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPago>> listar() {
        List<MetodoPago> metodoPago = metodoPagoService.listaMetodosPago();
        if(metodoPago.size()>0){
            return ResponseEntity.ok(metodoPago);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<MetodoPago> crear(@Valid @RequestBody MetodoPago metodoPago) {
        return ResponseEntity.ok(metodoPagoService.crearMetodoPago(metodoPago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> actualizar(@PathVariable Long id, @RequestBody MetodoPago metodoPago) {
        return ResponseEntity.ok(metodoPagoService.actualizarMetodoPago(id, metodoPago));
    }

    @PatchMapping("actualizar-status/{id}")
    public ResponseEntity<String> ActualizarStatusMetodoPago(@PathVariable Long id,@RequestBody MetodoPago metodoPago){
        String mensaje = metodoPagoService.actualizarEstadoMetodoPago(id,metodoPago.getActivo());
        return ResponseEntity.ok(mensaje);
    }
}
