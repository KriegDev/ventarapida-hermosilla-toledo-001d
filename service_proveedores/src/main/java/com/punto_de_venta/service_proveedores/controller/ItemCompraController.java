package com.punto_de_venta.service_proveedores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_proveedores.model.ItemCompra;
import com.punto_de_venta.service_proveedores.service.ItemCompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/items-compras")
public class ItemCompraController {

    @Autowired
    private ItemCompraService itemCompraService;

    @PostMapping  
    public ResponseEntity<ItemCompra> crearItemCompra(@Valid @RequestBody ItemCompra itemCompra){
        return ResponseEntity.ok(itemCompraService.crearItemCompra(itemCompra));
    }

    @GetMapping 
    public ResponseEntity<List<ItemCompra>> listarItemsCompra(){
        List<ItemCompra> lista = itemCompraService.listarItemsCompra();
        if(lista.size()>0){
            return ResponseEntity.ok(lista);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCompra> buscarItemCompraId(@PathVariable Long id){
        ItemCompra item = itemCompraService.buscarItemCompraId(id);
        if(item!=null){
            return ResponseEntity.ok(item);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
