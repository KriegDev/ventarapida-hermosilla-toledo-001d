package com.punto_de_venta.service_inventario.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.punto_de_venta.service_inventario.model.Stock;

import jakarta.transaction.Transactional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{

    @Query("SELECT s FROM Stock s WHERE s.cantidad <= s.stockMinimo")
    List<Stock> findByBajoStockMinimo();

    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.stockMinimo = :nuevoMinimo WHERE s.id = :id")
    Long updateStockMinimo(@Param("id") Long id, @Param("nuevoMinimo") BigDecimal nuevoMinimo);
}
