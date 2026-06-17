package com.punto_de_venta.service_facturacion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punto_de_venta.service_facturacion.model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long>{

    List<Factura> findAllByOrderByFechaEmisionAsc();
}
