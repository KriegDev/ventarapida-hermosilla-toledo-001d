package com.punto_de_venta.service_proveedores.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.punto_de_venta.service_proveedores.model.ItemCompra;
import com.punto_de_venta.service_proveedores.model.OrdenCompra;
import com.punto_de_venta.service_proveedores.repository.ItemCompraRepository;
import com.punto_de_venta.service_proveedores.repository.OrdenCompraRepository;

import jakarta.transaction.Transactional;

@Service

public class ItemCompraService {

    @Autowired 
    ItemCompraRepository itemCompraRepository;

    @Autowired
    OrdenCompraService ordenCompraService;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;


    @Transactional
    public ItemCompra crearItemCompra(ItemCompra itemCompra){
        
        OrdenCompra orden = ordenCompraService.buscarOrdenCompraId(
            itemCompra.getOrdenCompra().getId()
        );

        itemCompra.setOrdenCompra(orden);

        BigDecimal subtotal = itemCompra.getCantidad()
        .multiply(BigDecimal.valueOf(itemCompra.getCostoUnitario()));

        orden.setCostoTotal(orden.getCostoTotal() + subtotal.longValue());

        ordenCompraRepository.save(orden);
        return itemCompraRepository.save(itemCompra);
    }

    public List<ItemCompra> listarItemsCompra(){
        return itemCompraRepository.findAll();
    }

    public ItemCompra buscarItemCompraId(Long id){
        return itemCompraRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("El Item de compra que desea buscar no existe"));
    }
}
