package com.punto_de_venta.service_proveedores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.punto_de_venta.service_proveedores.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long>{

}
