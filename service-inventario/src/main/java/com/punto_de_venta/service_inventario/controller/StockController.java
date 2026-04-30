package com.punto_de_venta.service_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_inventario.model.Stock;
import com.punto_de_venta.service_inventario.service.InventarioService;

@RestController
@RequestMapping("api/v1/stock")
public class StockController {
    @Autowired
    private InventarioService is;

    @GetMapping("/{id}")
    public ResponseEntity<Stock> obtenerStockId(@PathVariable Long id){
        return ResponseEntity.ok(is.obtenerStockId(id));
    }

    @GetMapping("/bajo-stock")
    public ResponseEntity<List<Stock>> alertaBajoStock(){
        List<Stock> ll = is.alertaBajoStock();
        if (ll.size()>0) {
            return ResponseEntity.ok(ll);
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("stock-minimo/{id}")
    public ResponseEntity<Stock> actualizarStockMinimo(@PathVariable Long id, @RequestBody Stock stock){
        is.actualizarStockMinimo(id, stock.getStockMinimo());
        return ResponseEntity.ok(is.obtenerStockId(id));
    }
}
