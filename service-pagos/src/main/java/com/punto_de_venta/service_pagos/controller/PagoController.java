package com.punto_de_venta.service_pagos.controller;

import com.punto_de_venta.service_pagos.dto.PagoDTO;
import com.punto_de_venta.service_pagos.model.Pago;
import com.punto_de_venta.service_pagos.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pago")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Gestión de Pagos", description = "Endpoints para procesar, confirmar, listar y anular pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Procesar pago", description = "Procesa un pago asociado a una orden utilizando un método de pago registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago procesado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Recurso relacionado no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/procesar")
    public ResponseEntity<?> procesar(@RequestBody PagoDTO pagoDto) {
    Pago resultado = pagoService.procesarPago(pagoDto);
    return ResponseEntity.ok(resultado);
}

    @Operation(summary = "Confirmar pago Flow", description = "Confirma un pago utilizando el token recibido desde Flow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago confirmado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/confirmar-flow")
    public ResponseEntity<Pago> confirmarPago(@RequestParam("token") String token) {
        return ResponseEntity.ok(pagoService.confirmarPagoFlow(token.trim()));
    }

    @Operation(summary = "Listar pagos", description = "Obtiene la lista completa de pagos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen pagos disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Pago>> listarTodos() {
        List<Pago> pagos= pagoService.listarPagos();
        if(pagos.size()>0){
            return ResponseEntity.ok(pagos);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Anular pago", description = "Anula un pago existente cambiando su estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago anulado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Pago> anular(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.anularPago(id));
    }
}
