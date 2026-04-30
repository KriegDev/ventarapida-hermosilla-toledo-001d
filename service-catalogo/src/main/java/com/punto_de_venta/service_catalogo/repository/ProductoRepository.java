package com.punto_de_venta.service_catalogo.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.punto_de_venta.service_catalogo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Producto findBySku(String sku);
}
