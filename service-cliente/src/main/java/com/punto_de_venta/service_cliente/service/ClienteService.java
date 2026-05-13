package com.punto_de_venta.service_cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punto_de_venta.service_cliente.model.Cliente;
import com.punto_de_venta.service_cliente.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Transactional
    public Cliente crearCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }

    public Cliente buscarClienteRun(Long run){
        return clienteRepository.findByRun(run)
        .orElseThrow(() -> new RuntimeException("El cliente no existe"));
    }

    public Cliente buscarClienteId(Long id){
        return clienteRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("El Id Cliente no existe"));
    }

    @Transactional
    public Cliente actualizarDatosContactoCliente(Long id, String correo, Long telefono){
        Cliente clienteActualizar = clienteRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("El Id Cliente no existe"));
        clienteActualizar.setCorreo(correo);
        clienteActualizar.setTelefono(telefono);
        return clienteRepository.save(clienteActualizar);
    }

    @Transactional
    public String actualizarEstadoCliente(Long id, Boolean estado){
        Cliente clienteActualizar = clienteRepository.findById(id)
        .orElse(null);
        if(clienteActualizar==null){
            return "Cliente no encontrado";
        }else{
            clienteActualizar.setActivo(estado);
            clienteRepository.save(clienteActualizar);
            return "El estado del cliente ha sido actualizado";
        }
    }
}
