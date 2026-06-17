package com.punto_de_venta.service_reportes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.punto_de_venta.service_reportes.model.HistorialReporte;
import com.punto_de_venta.service_reportes.repository.HistorialReporteRepository;

@Service
public class ReporteService {

    @Autowired
    private HistorialReporteRepository reporteRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String URL_VENTAS = "http://localhost:4425/api/v1/ordenes";
    private final String URL_INVENTARIO = "http://localhost:4425/api/v1/stock";

    public HistorialReporte generarReporteConsolidado(String tipo, String usuarioSolicitante){

        Long totalVentas = 0L;
        Long alertasStock = 0L;

        try {
            totalVentas = webClientBuilder.build()
            .get()
            .uri(URL_VENTAS+"/total-recaudado")
            .retrieve()
            .bodyToMono(Long.class)
            .block();
        } catch (Exception e) {
            System.err.println("Advertencia: no se pudo obtener el total de ventas. "+e.getMessage());
        }

        try {
            alertasStock = webClientBuilder.build()
            .get()
            .uri(URL_INVENTARIO+"/alertas-count")
            .retrieve()
            .bodyToMono(Long.class)
            .block();
        } catch (Exception e) {
            System.err.println("Advertencia: no se pudo obtener las alertas de stock. "+e.getMessage());
        }

        HistorialReporte nuevoReporte = new HistorialReporte();
        nuevoReporte.setTipoReporte(tipo);
        nuevoReporte.setFechaGeneracion(LocalDateTime.now());
        nuevoReporte.setTotalVentasCalculadas(totalVentas != null ? totalVentas : 0L);
        nuevoReporte.setAlertasStockDetectadas(alertasStock != null ? alertasStock : 0L);
        nuevoReporte.setGeneradoPor(usuarioSolicitante);

        return reporteRepository.save(nuevoReporte);
    }

    public List<HistorialReporte> obtenerHistorial(){
        return reporteRepository.findAll();
    }

}
