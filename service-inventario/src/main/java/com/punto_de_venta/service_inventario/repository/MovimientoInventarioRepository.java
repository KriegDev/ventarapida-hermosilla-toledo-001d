package com.punto_de_venta.service_inventario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.punto_de_venta.service_inventario.model.MovimientoInventario;
import java.time.LocalDateTime;


@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long>{

    List<MovimientoInventario> findByFechaMovimiento(LocalDateTime fechaMovimiento);

    Optional<List<MovimientoInventario>> findByFechaMovimientoBetween(LocalDateTime inicio, LocalDateTime fin);

    Optional<List<MovimientoInventario>> findByFechaMovimientoAfter(LocalDateTime fechaMovimiento);
}
