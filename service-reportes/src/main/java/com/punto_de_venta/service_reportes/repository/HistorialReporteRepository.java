package com.punto_de_venta.service_reportes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punto_de_venta.service_reportes.model.HistorialReporte;

public interface HistorialReporteRepository extends JpaRepository<HistorialReporte, Long>{

    List<HistorialReporte> findByTipoReporteOrderByFechaGeneracionDesc(String tipoReporte);
}
