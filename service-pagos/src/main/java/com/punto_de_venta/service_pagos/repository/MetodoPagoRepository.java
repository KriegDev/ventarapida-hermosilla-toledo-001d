package com.punto_de_venta.service_pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punto_de_venta.service_pagos.model.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago,Long>{

}
