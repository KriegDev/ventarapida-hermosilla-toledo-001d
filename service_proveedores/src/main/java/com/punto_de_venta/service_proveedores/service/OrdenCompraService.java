package com.punto_de_venta.service_proveedores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.punto_de_venta.service_proveedores.dto.MovimientoInventarioDTO;
import com.punto_de_venta.service_proveedores.model.ItemCompra;
import com.punto_de_venta.service_proveedores.model.OrdenCompra;
import com.punto_de_venta.service_proveedores.repository.OrdenCompraRepository;

import jakarta.transaction.Transactional;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private WebClient.Builder webClientBuilder;


    @Transactional
    public OrdenCompra crearOrden(OrdenCompra ordenCompra){
        
        proveedorService.buscarProveedorId(
            ordenCompra.getProveedor().getId()
        );

        ordenCompra.setCostoTotal(0L);
        return ordenCompraRepository.save(ordenCompra);
    }

     public List<OrdenCompra> listarOrdenes() {
        List<OrdenCompra> ordenes = ordenCompraRepository.findAll();

        for (OrdenCompra orden : ordenes) {
            cargarDatosProducto(orden);
        }

        return ordenes;
    }

    private void cargarDatosProducto(OrdenCompra orden){
        if(orden.getItems() != null){
            for(ItemCompra item : orden.getItems()){
                try{
                    Object producto = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:4425/api/v1/productos/" + item.getIdProducto())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

                    item.setDatosProducto(producto);
                }catch(Exception e){
                    item.setDatosProducto("Información del producto no disponible");
                }
            }
        }
    }

    public OrdenCompra buscarOrdenCompraId(Long id) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La orden de compra no existe"));

        cargarDatosProducto(orden);

        return orden;
    }

    @Transactional
    public OrdenCompra actualizarStatusOrdenCompra(Long id, OrdenCompra ordenCompra){
    OrdenCompra ordenActualizar = ordenCompraRepository.findById(id)
    .orElseThrow(()-> new RuntimeException("La orden de compra que quiere actualizar no existe"));
    
    String statusAnterior = ordenActualizar.getStatus();

    ordenActualizar.setStatus(ordenCompra.getStatus());
    OrdenCompra ordenGuardada = ordenCompraRepository.save(ordenActualizar);

    if(!statusAnterior.equalsIgnoreCase("RECIBIDA") 
            && ordenGuardada.getStatus().equalsIgnoreCase("RECIBIDA")){

        for (ItemCompra item : ordenGuardada.getItems()){

            MovimientoInventarioDTO movimiento = new MovimientoInventarioDTO(item.getCantidad(), "ENTRADA");
        
            webClientBuilder.build()
            .post()
            .uri("http://localhost:4421/api/v1/movimiento-inventario/nuevo/" + item.getIdProducto())
            .bodyValue(movimiento)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
        }
    }

    cargarDatosProducto(ordenGuardada);
    return ordenGuardada;
    }
}


