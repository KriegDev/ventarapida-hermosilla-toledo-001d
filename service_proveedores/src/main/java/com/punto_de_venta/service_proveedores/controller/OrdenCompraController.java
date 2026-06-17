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

import com.punto_de_venta.service_proveedores.model.OrdenCompra;
import com.punto_de_venta.service_proveedores.service.OrdenCompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/ordenes-compras")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @PostMapping 
    public ResponseEntity<OrdenCompra> crearOrdenCompra(@Valid @RequestBody OrdenCompra ordenCompra){
        return ResponseEntity.ok(ordenCompraService.crearOrden(ordenCompra));
    }

    @GetMapping 
    public ResponseEntity<List<OrdenCompra>> listarOrdenesCompra(){
        List<OrdenCompra> lista = ordenCompraService.listarOrdenes();
        if(lista.size()>0){
            return ResponseEntity.ok(lista);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompra> buscarOrdenCompraId(@PathVariable Long id){
        OrdenCompra ordenCompra = ordenCompraService.buscarOrdenCompraId(id);
        if(ordenCompra!=null){
            return ResponseEntity.ok(ordenCompra);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/cambiar-status/{id}")
    public ResponseEntity<OrdenCompra> actualizarStatusOrdenCompra(@PathVariable Long id,@RequestBody OrdenCompra ordenCompra){
        OrdenCompra ordenCompraActualizar = ordenCompraService.actualizarStatusOrdenCompra(id, ordenCompra);
        return ResponseEntity.ok(ordenCompraActualizar);
    }


}
