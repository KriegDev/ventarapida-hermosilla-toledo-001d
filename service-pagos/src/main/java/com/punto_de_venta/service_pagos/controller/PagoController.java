package com.punto_de_venta.service_pagos.controller;

import com.punto_de_venta.service_pagos.dto.PagoDTO;
import com.punto_de_venta.service_pagos.model.Pago;
import com.punto_de_venta.service_pagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/procesar")
public ResponseEntity<?> procesar(@RequestBody PagoDTO pagoDto) {
    Pago resultado = pagoService.procesarPago(pagoDto);
    return ResponseEntity.ok(resultado);
}

    @GetMapping("/confirmar-flow")
    public ResponseEntity<Pago> confirmarPago(@RequestParam("token") String token) {
        return ResponseEntity.ok(pagoService.confirmarPagoFlow(token.trim()));
    }

    @GetMapping
    public ResponseEntity<List<Pago>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pago> anular(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.anularPago(id));
    }
}
