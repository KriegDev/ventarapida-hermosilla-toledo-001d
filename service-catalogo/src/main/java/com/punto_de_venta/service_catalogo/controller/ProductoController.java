package com.punto_de_venta.service_catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_catalogo.model.Producto;
import com.punto_de_venta.service_catalogo.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto){
        return ResponseEntity.ok(productoService.crearProducto(producto));
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        List<Producto> productos= productoService.listarProductos();
        if(productos.size()>0){
            return ResponseEntity.ok(productos);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProductoId(@PathVariable Long id){
        Producto producto = productoService.buscarProductoId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<Producto> buscarProductSku(@PathVariable String sku){
        Producto producto = productoService.buscarProductoSku(sku);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id,@Valid  @RequestBody Producto producto){
        Producto productoActualizar = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id){
        String productoEliminar = productoService.eliminarProducto(id);
        return ResponseEntity.ok(productoEliminar);
    }

    @GetMapping("/existe/{id}")
    public ResponseEntity<Boolean> existe(@PathVariable Long id){
        Boolean existe = productoService.existe(id);
        return ResponseEntity.ok(existe);
    }



}
