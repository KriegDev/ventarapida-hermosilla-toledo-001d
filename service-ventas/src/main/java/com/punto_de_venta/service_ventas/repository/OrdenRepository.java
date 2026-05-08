package com.punto_de_venta.service_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.punto_de_venta.service_ventas.model.Orden;
import java.util.List;
import java.util.Optional;


@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long>{
    Optional<Orden> findByNumeroOrden(Long numeroOrden);

    List<Orden> findByStatus(String status);
}
