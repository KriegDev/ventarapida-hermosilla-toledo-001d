package com.punto_de_venta.service_cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_cliente.model.Cliente;
import com.punto_de_venta.service_cliente.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente){
        return ResponseEntity.ok(clienteService.crearCliente(cliente));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> clientes= clienteService.listarClientes();
        if(clientes.size()>0){
            return ResponseEntity.ok(clientes);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClienteId(@PathVariable Long id){
        Cliente cliente = clienteService.buscarClienteId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/run/{run}")
    public ResponseEntity<Cliente> buscarClienteRun(@PathVariable Long run){
        Cliente cliente = clienteService.buscarClienteRun(run);
        return ResponseEntity.ok(cliente);
    }

    @PatchMapping("actualizar-datos-contacto/{id}")
    public ResponseEntity<Cliente> actualizarDatosContactoCliente(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente actualizarCliente = clienteService.actualizarDatosContactoCliente(id, cliente.getCorreo(), cliente.getTelefono());
        return ResponseEntity.ok(actualizarCliente);
    }

    @PatchMapping("actualizar-status/{id}")
    public ResponseEntity<String> ActualizarStatusCliente(@PathVariable Long id,@RequestBody Cliente cliente){
        String mensaje = clienteService.actualizarEstadoCliente(id, cliente.getActivo());
        return ResponseEntity.ok(mensaje);
    }
}
