package com.punto_de_venta.service_pagos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punto_de_venta.service_pagos.model.MetodoPago;
import com.punto_de_venta.service_pagos.repository.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Transactional
    public MetodoPago crearMetodoPago(MetodoPago metodoPago){
        return metodoPagoRepository.save(metodoPago);
    }

    public List<MetodoPago> listaMetodosPago(){
        return metodoPagoRepository.findAll();
    }

    public MetodoPago buscarMetodoPagoId(Long id){
        return metodoPagoRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("El metodo de pago no existe"));
    }

    @Transactional
    public MetodoPago actualizarMetodoPago(Long id, MetodoPago metodoPago){
        MetodoPago metodoPagoExistente = metodoPagoRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Metodo de pago no encontrado"));

        metodoPagoExistente.setNombre(metodoPago.getNombre());
        metodoPagoExistente.setActivo(metodoPago.getActivo());
        return metodoPagoRepository.save(metodoPagoExistente);
    }

    @Transactional
    public String anularMetodoPago(Long id){
        MetodoPago metodoPago = metodoPagoRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Metodo de pago no encontrado") );
        metodoPago.setActivo(false);
        metodoPagoRepository.save(metodoPago);
        return "Metodo de pago anulado";
    }

    public boolean esMetodoExterno(Long id){
        return id.equals(4L);
    }
}
