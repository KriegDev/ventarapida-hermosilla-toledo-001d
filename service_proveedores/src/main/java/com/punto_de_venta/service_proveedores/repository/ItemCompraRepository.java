package com.punto_de_venta.service_proveedores.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.punto_de_venta.service_proveedores.model.ItemCompra;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long>{
    
}
