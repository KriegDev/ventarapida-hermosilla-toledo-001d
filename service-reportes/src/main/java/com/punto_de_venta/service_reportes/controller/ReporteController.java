package com.punto_de_venta.service_reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_reportes.model.HistorialReporte;
import com.punto_de_venta.service_reportes.service.ReporteService;

@RestController
@RequestMapping("api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @PostMapping("/generar")
    public ResponseEntity<HistorialReporte> generarReporte(
        @RequestParam(defaultValue = "GENERAL") String tipo,
        @RequestParam(defaultValue = "SYSTEM") String usuario)
    {
        HistorialReporte reporte = reporteService.generarReporteConsolidado(tipo, usuario);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<HistorialReporte>> listarHistorial(){
        return ResponseEntity.ok(reporteService.obtenerHistorial());
    }
}
