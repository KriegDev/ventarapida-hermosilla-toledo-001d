package com.punto_de_venta.service_reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_reportes.model.HistorialReporte;
import com.punto_de_venta.service_reportes.service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/reportes")
@Tag(name = "Módulo de Reportes y Analítica", description = "Endpoints para la generación consolidada de métricas y consulta del historial de reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Operation(summary = "Generar un nuevo reporte consolidado", description = "Se comunica dinámicamente con los microservicios de Ventas e Inventario para agrupar las métricas de negocio (ventas totales y alertas de stock) y guarda un registro histórico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro procesado y creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación en el formato del JSON enviado", content = @Content)
    })
    @PostMapping("/generar")
    public ResponseEntity<HistorialReporte> generarReporte(
        @RequestParam(defaultValue = "GENERAL") String tipo,
        @RequestParam(defaultValue = "SYSTEM") String usuario)
    {
        HistorialReporte reporte = reporteService.generarReporteConsolidado(tipo, usuario);
        return ResponseEntity.ok(reporte);
    }

    @Operation(summary = "Listar historial de reportes", description = "Obtiene una lista completa de todos los reportes analíticos que han sido generados y almacenados previamente en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping("/historial")
    public ResponseEntity<List<HistorialReporte>> listarHistorial(){
        return ResponseEntity.ok(reporteService.obtenerHistorial());
    }
}
