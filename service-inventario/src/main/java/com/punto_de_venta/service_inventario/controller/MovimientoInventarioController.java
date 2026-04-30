package com.punto_de_venta.service_inventario.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_inventario.model.MovimientoInventario;
import com.punto_de_venta.service_inventario.service.InventarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/movimiento-inventario")
public class MovimientoInventarioController {

    @Autowired
    private InventarioService is; 
    
    @PostMapping("/nuevo/{id}")
    public ResponseEntity<MovimientoInventario> crear(@Valid @RequestBody MovimientoInventario mov, @PathVariable Long id){
        return ResponseEntity.ok(is.nuevoMovimiento(id, mov));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventario> buscarPorId(@PathVariable Long id){
         return is.obtenerMovimientoPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/{dia}")
    public ResponseEntity<List<MovimientoInventario>> buscarPorDia(@PathVariable LocalDate dia){
        return is.obtenerMovimientosPorDia(dia).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
        
    }

    @GetMapping("/buscar/{fecha}")
    public ResponseEntity<List<MovimientoInventario>> buscarMovimientosDesde(@PathVariable LocalDateTime fecha){
        return is.obtenerMovimientosDesde(fecha).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/entre/{ini}/y/{fin}")
    public ResponseEntity<List<MovimientoInventario>> buscarEntreFechas(@PathVariable LocalDateTime ini, @PathVariable LocalDateTime fin){
        return is.obtenerMovimientosEntre(ini, fin).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping ResponseEntity<List<MovimientoInventario>> listarMovimientos(){
        List<MovimientoInventario> ll = is.listarMovimientos();
        if (ll.size()>0) {
            return ResponseEntity.ok(ll);
        }
        return ResponseEntity.noContent().build();
    }
}
