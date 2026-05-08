package com.punto_de_venta.service_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.punto_de_venta.service_ventas.model.Detalle;

@Repository
public interface DetalleRepository extends JpaRepository <Detalle, Long>{

}
