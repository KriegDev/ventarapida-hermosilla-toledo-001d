package com.punto_de_venta.service_proveedores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punto_de_venta.service_proveedores.model.Proveedor;
import com.punto_de_venta.service_proveedores.repository.ProveedorRepository;

import jakarta.transaction.Transactional;

@Service
public class ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional
    public Proveedor crearProveedor(Proveedor proveedor){
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> listarProveedores(){
        return proveedorRepository.findAll();
    }

    public Proveedor buscarProveedorId(Long id){
        return proveedorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("El proveedor no existe"));
    }

    @Transactional
    public Proveedor actualizarProveedor(Long id, Proveedor proveedor){
        Proveedor proveedorExiste = proveedorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("El proveedor que desea actualizar no existe"));
        proveedorExiste.setNombreContacto(proveedor.getNombreContacto());
        proveedorExiste.setEmail(proveedor.getEmail());
        return proveedorRepository.save(proveedorExiste);
    }

    @Transactional
    public String actualizarEstadoProveedor(Long id,Proveedor proveedor){
        Proveedor proveedorExistente = proveedorRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("El proovedor que desea eliminar no existe"));
        proveedorExistente.setActivo(proveedor.getActivo());
        proveedorRepository.save(proveedorExistente);
        return "El estado del proveedor ha sido cambiado a: "+proveedor.getActivo();
    }
}
