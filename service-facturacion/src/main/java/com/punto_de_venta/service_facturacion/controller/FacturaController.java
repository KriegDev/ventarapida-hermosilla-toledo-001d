package com.punto_de_venta.service_facturacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_facturacion.dto.FacturaDTO;
import com.punto_de_venta.service_facturacion.model.Factura;
import com.punto_de_venta.service_facturacion.service.FacturaService;

@RestController
@RequestMapping("api/v1/facturas")
public class FacturaController {

    @Autowired
    private FacturaService fts;

    @PostMapping("/generar")
    public ResponseEntity<Factura> generarFactura(@RequestBody FacturaDTO peticion){
        Factura nuevaFactura = fts.generarFactura(peticion.getIdOrden(), peticion.getMontoTotal());
        return ResponseEntity.ok(nuevaFactura);
    }

    @GetMapping()
    public ResponseEntity<List<Factura>> listarFacturas(){
        List<Factura> listaFacturas = fts.listarFacturas();
        if (listaFacturas.size()>0) {
            return ResponseEntity.ok(listaFacturas);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cronologico")
    public ResponseEntity<List<Factura>> listarPorFecha(){
        List<Factura> listaFacturas = fts.listarFacturasPorFecha();
        if (listaFacturas.size()>0) {
            return ResponseEntity.ok(listaFacturas);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Factura> buscarPorId(@PathVariable Long id){
        Factura resultado = fts.buscarFacturaPorId(id);
        if (resultado!=null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }
}
