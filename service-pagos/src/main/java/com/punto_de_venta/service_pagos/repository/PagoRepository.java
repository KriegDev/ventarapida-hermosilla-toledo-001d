package com.punto_de_venta.service_pagos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punto_de_venta.service_pagos.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long>{

    Optional<Pago> findByFlowToken(String flowToken);
    
}
