package com.punto_de_venta.service_cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_cliente.model.Cliente;
import com.punto_de_venta.service_cliente.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/clientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Gestión de Clientes", description = "Endpoints para registrar, listar, buscar y actualizar clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Crear cliente", description = "Permite registrar un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud")
    })
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente){
        return ResponseEntity.ok(clienteService.crearCliente(cliente));
    }

    @Operation(summary = "Listar clientes", description = "Obtiene la lista completa de clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen clientes disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> clientes= clienteService.listarClientes();
        if(clientes.size()>0){
            return ResponseEntity.ok(clientes);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Buscar cliente por ID", description = "Busca un cliente específico mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClienteId(@PathVariable Long id){
        Cliente cliente = clienteService.buscarClienteId(id);
        if(cliente!=null){
            return ResponseEntity.ok(cliente);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar cliente por RUN", description = "Busca un cliente específico mediante su RUN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/run/{run}")
    public ResponseEntity<Cliente> buscarClienteRun(@PathVariable Long run){
        Cliente cliente = clienteService.buscarClienteRun(run);
        if(cliente!=null){
            return ResponseEntity.ok(cliente);
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    @Operation(summary = "Actualizar datos de contacto", description = "Permite actualizar el correo y teléfono de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos de contacto actualizados correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("actualizar-datos-contacto/{id}")
    public ResponseEntity<Cliente> actualizarDatosContactoCliente(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente actualizarCliente = clienteService.actualizarDatosContactoCliente(id, cliente.getCorreo(), cliente.getTelefono());
        return ResponseEntity.ok(actualizarCliente);
    }

    @Operation(summary = "Actualizar estado del cliente", description = "Permite activar o desactivar un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del cliente actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("actualizar-status/{id}")
    public ResponseEntity<String> ActualizarStatusCliente(@PathVariable Long id,@RequestBody Cliente cliente){
        String mensaje = clienteService.actualizarEstadoCliente(id, cliente.getActivo());
        return ResponseEntity.ok(mensaje);
    }
}
