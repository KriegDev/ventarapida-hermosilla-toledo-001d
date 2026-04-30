package com.punto_de_venta.service_catalogo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punto_de_venta.service_catalogo.model.Producto;
import com.punto_de_venta.service_catalogo.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //CrearProducto
    @Transactional //Una anotación que permite guardar los cambios si es que salen bien, si no, se deshace de todo
    public Producto crearProducto(Producto producto){
        return productoRepository.save(producto);
    }

    //Listar Productos
    public List<Producto> listarProductos(){
        return productoRepository.findAll();
    }

    //buscar Producto Id
    public Producto buscarProductoId(Long id){
        return productoRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("El producto no existe") );
    }

    //Buscar por Sku
    public Producto buscarProductoSku(String sku){
        Producto productoId = productoRepository.findBySku(sku);
        if(productoId != null){
            return productoId;
        }else{
            throw new RuntimeException("El Producto no existe");
        }
    }

    //Verificar si el producto existe para el metodo de Nuevo movimiento del microservicio Inventario
    public Boolean existe (Long id){
        Producto productoId = productoRepository.findById(id).orElse(null);
        if(productoId != null){
            return true;
        }else{
            return false;
        }
    } 

    //Actualizar Producto
    @Transactional
    public Producto actualizarProducto(Long id, Producto producto){
        Producto productoExistente = productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            productoExistente.setSku(producto.getSku());
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setPrecioBase(producto.getPrecioBase());
            productoExistente.setGranel(producto.getGranel());
            productoExistente.setCategoria(producto.getCategoria());
            return productoRepository.save(productoExistente);
    }
    

    //Eliminar producto
    @Transactional
    public String eliminarProducto(Long id){
        Producto producto = productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La id del producto que desea eliminar no existe"));
        productoRepository.delete(producto);
        return "El producto ha sido eliminado exitosamente";
    }


}
