package com.punto_de_venta.service_pagos.controller;

import com.punto_de_venta.service_pagos.model.MetodoPago;
import com.punto_de_venta.service_pagos.service.MetodoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/metodos-pago")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Gestión de Métodos de Pago", description = "Endpoints para administrar los métodos de pago del sistema")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Operation(summary = "Listar métodos de pago", description = "Obtiene la lista completa de métodos de pago registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen métodos de pago disponibles")
    })
    @GetMapping
    public ResponseEntity<List<MetodoPago>> listar() {
        List<MetodoPago> metodoPago = metodoPagoService.listaMetodosPago();
        if(metodoPago.size()>0){
            return ResponseEntity.ok(metodoPago);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Crear método de pago", description = "Permite registrar un nuevo método de pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Método de pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud")
    })
    @PostMapping
    public ResponseEntity<MetodoPago> crear(@Valid @RequestBody MetodoPago metodoPago) {
        return ResponseEntity.ok(metodoPagoService.crearMetodoPago(metodoPago));
    }

    @Operation(summary = "Actualizar método de pago", description = "Permite modificar los datos de un método de pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Método de pago actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Método de pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MetodoPago> actualizar(@PathVariable Long id, @RequestBody MetodoPago metodoPago) {
        return ResponseEntity.ok(metodoPagoService.actualizarMetodoPago(id, metodoPago));
    }

    @Operation(summary = "Actualizar estado del método de pago", description = "Permite activar o desactivar un método de pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Método de pago no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("actualizar-status/{id}")
    public ResponseEntity<String> ActualizarStatusMetodoPago(@PathVariable Long id,@RequestBody MetodoPago metodoPago){
        String mensaje = metodoPagoService.actualizarEstadoMetodoPago(id,metodoPago.getActivo());
        return ResponseEntity.ok(mensaje);
    }
}
