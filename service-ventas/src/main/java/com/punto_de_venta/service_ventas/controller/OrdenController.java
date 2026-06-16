package com.punto_de_venta.service_ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_ventas.model.Orden;
import com.punto_de_venta.service_ventas.service.VentaService;


@RestController
@RequestMapping("api/v1/ordenes")
public class OrdenController {
    @Autowired
    private VentaService vs;

    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestBody Orden orden){
        Orden nuevaOrden = vs.procesarVenta(orden);
        return ResponseEntity.ok(nuevaOrden);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> buscarPorId(@PathVariable Long id){
        Orden orden = vs.buscarPorId(id);
        if (orden!=null) {
            return ResponseEntity.ok(orden);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    @GetMapping("/numero/{nro}")
    public ResponseEntity<Orden> buscarPorNumero(@PathVariable Long nro){
        Orden orden = vs.buscarPorNumeroOrden(nro);
        if (orden!=null) {
            return ResponseEntity.ok(orden);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Orden>> listarPorStatus(@PathVariable String status){
        List<Orden> listaOrdenes = vs.listarPorStatus(status);
        if (listaOrdenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaOrdenes);
    }

    @GetMapping
    public ResponseEntity<List<Orden>> listarOrdenes(){
        List<Orden> listaOrdenes = vs.listarOrdenes();
        if (listaOrdenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaOrdenes);
    }
}
