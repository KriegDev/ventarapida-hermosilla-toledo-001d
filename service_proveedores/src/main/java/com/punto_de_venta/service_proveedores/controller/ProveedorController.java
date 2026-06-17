package com.punto_de_venta.service_proveedores.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_proveedores.model.Proveedor;
import com.punto_de_venta.service_proveedores.service.ProveedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@Valid @RequestBody Proveedor proveedor){
        return ResponseEntity.ok(proveedorService.crearProveedor(proveedor));
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores(){
        List<Proveedor> proveedores = proveedorService.listarProveedores();
        if(proveedores.size()>0){
            return ResponseEntity.ok(proveedores);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscarProveedorId(@PathVariable Long id){
        Proveedor proveedor = proveedorService.buscarProveedorId(id);
        if(proveedor!=null){
            return ResponseEntity.ok(proveedor);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @Valid @RequestBody Proveedor proveedor){
        Proveedor proveedorActualizar = proveedorService.actualizarProveedor(id, proveedor);
        return ResponseEntity.ok(proveedorActualizar);
    }

    @PatchMapping("/cambiar-status/{id}")
    public ResponseEntity<String> actualizarProveedorStatus(@PathVariable Long id,@RequestBody Proveedor proveedor){
        String mensajeProveedorActualizar = proveedorService.actualizarEstadoProveedor(id, proveedor);
        return ResponseEntity.ok(mensajeProveedorActualizar);
    }
}
