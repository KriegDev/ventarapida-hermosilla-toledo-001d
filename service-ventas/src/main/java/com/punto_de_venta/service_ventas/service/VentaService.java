package com.punto_de_venta.service_ventas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.punto_de_venta.service_ventas.dto.MovimientoDTO;
import com.punto_de_venta.service_ventas.dto.PagoDTO;
import com.punto_de_venta.service_ventas.dto.ProductoDTO;
import com.punto_de_venta.service_ventas.model.Detalle;
import com.punto_de_venta.service_ventas.model.Orden;
import com.punto_de_venta.service_ventas.repository.OrdenRepository;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
public class VentaService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Transactional
    public Orden procesarVenta(Orden orden){
        Long totalOrden = 0L;

        for (Detalle detalle : orden.getDetalles()) {
            detalle.setOrden(orden);

            //Se crea un DTO con los detalles necesarios para procesar la venta
            ProductoDTO prod = webClientBuilder.build()
            .get().uri("http://localhost:4425/api/v1/productos/"+detalle.getIdProducto())
            .retrieve()
            .bodyToMono(ProductoDTO.class).block();

            if (prod != null) {
                detalle.setPrecioUnitario(prod.getPrecio());
                detalle.setDatosProducto(prod);
            }

            //Aquí modificamos el stock del producto
            webClientBuilder.build()
            .post()
            .uri("http://localhost:4425/api/v1/inventario/movimiento/"+detalle.getIdProducto())
            .bodyValue(new MovimientoDTO("Salida", detalle.getCantidad()))
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> {
                return Mono.error(new RuntimeException("Error al actualizar inventario"));
            }).bodyToMono(Void.class).block();

            Long precio = detalle.getPrecioUnitario();

            totalOrden+=Math.round(precio* detalle.getCantidad().doubleValue());
        }
        orden.setMontoTotal(totalOrden);
        orden.setFechaVenta(LocalDate.now());
        orden.setStatus("Pendiente");

        try{
            webClientBuilder.build()
            .post()
            .uri("http://localhost:4425/api/v1/pagos/procesar")
            .bodyValue(new PagoDTO(orden.getNumeroOrden(), totalOrden, orden.getIdMetodoPago()))
            .retrieve()
            .bodyToMono(Void.class).block();

            orden.setStatus("PAGADO");

        } catch (Exception e){
            orden.setStatus("Pago Rechazado");
            throw new RuntimeException("Venta Cancelada: Error en la pasarela de pago "+e.getMessage());
        }

        return ordenRepository.save(orden);
    }

    public Orden buscarPorId(Long id){
        Orden orden = ordenRepository.findById(id).orElseThrow(()-> new RuntimeException("Orden no encontrada con ID: "+id));
        enriquecerOrden(orden);
        return orden;
    }

    public Orden buscarPorNumeroOrden(Long nro){
        Orden orden = ordenRepository.findByNumeroOrden(nro).orElseThrow(()-> new RuntimeException("Orden no encontrada con el Número: "+nro));
        enriquecerOrden(orden);
        return orden;
    }

    public List<Orden> listarPorStatus(String status){
        List<Orden> ordenes = ordenRepository.findByStatus(status);

        ordenes.forEach(this::enriquecerOrden);

        return ordenes;
    }

    private void enriquecerOrden(Orden orden){
        for (Detalle detalle : orden.getDetalles()) {
            try{
                ProductoDTO prod = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:4425/api/v1/productos/"+detalle.getIdProducto())
                    .retrieve()
                    .bodyToMono(ProductoDTO.class).block();

                detalle.setDatosProducto(prod);
                
            } catch (Exception e){
                detalle.setDatosProducto("Información de catálogo no disponible");
            }
        }
    }



}
