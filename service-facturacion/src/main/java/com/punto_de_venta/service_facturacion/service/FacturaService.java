package com.punto_de_venta.service_facturacion.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.punto_de_venta.service_facturacion.model.Factura;
import com.punto_de_venta.service_facturacion.repository.FacturaRepository;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository ftr;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Factura generarFactura(Long idOrden, Long montoTotal){

        Factura nuevaFactura = new Factura();
        nuevaFactura.setIdOrden(idOrden);

        Long subtotal = Math.round(montoTotal / 1.19);
        Long impuestos = montoTotal - subtotal;

        nuevaFactura.setMontoSubtotal(subtotal);
        nuevaFactura.setImpuesto(BigDecimal.valueOf(impuestos));
        nuevaFactura.setMontoTotal(montoTotal);
        nuevaFactura.setFechaEmision(LocalDateTime.now());

        return ftr.save(nuevaFactura);
    }

    public List<Factura> listarFacturasPorFecha(){
        return ftr.findAllByOrderByFechaEmisionAsc();
    }

    public List<Factura> listarFacturas(){
        return ftr.findAll();
    }

    public Factura buscarFacturaPorId(Long id){
        return ftr.findById(id).orElse(null);
    }

    //No se considera crear un método para actualizar o eliminar por criterios de información sensible y auditoria
}
