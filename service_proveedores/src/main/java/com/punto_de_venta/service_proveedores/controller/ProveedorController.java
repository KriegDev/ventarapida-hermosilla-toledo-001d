package com.punto_de_venta.service_proveedores.controller;

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

import com.punto_de_venta.service_proveedores.model.Proveedor;
import com.punto_de_venta.service_proveedores.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/proveedores")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Gestión de Proveedores", description = "Endpoints para registrar, listar, buscar y actualizar proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Operation(summary = "Crear proveedor", description = "Permite registrar un nuevo proveedor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud")
    })
    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@Valid @RequestBody Proveedor proveedor){
        return ResponseEntity.ok(proveedorService.crearProveedor(proveedor));
    }

    @Operation(summary = "Listar proveedores", description = "Obtiene la lista completa de proveedores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen proveedores disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Proveedor>> listarProveedores(){
        List<Proveedor> proveedores = proveedorService.listarProveedores();
        if(proveedores.size()>0){
            return ResponseEntity.ok(proveedores);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Buscar proveedor por ID", description = "Busca un proveedor específico mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscarProveedorId(@PathVariable Long id){
        Proveedor proveedor = proveedorService.buscarProveedorId(id);
        if(proveedor!=null){
            return ResponseEntity.ok(proveedor);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar proveedor", description = "Permite modificar los datos de contacto de un proveedor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @Valid @RequestBody Proveedor proveedor){
        Proveedor proveedorActualizar = proveedorService.actualizarProveedor(id, proveedor);
        return ResponseEntity.ok(proveedorActualizar);
    }

    @Operation(summary = "Actualizar estado del proveedor", description = "Permite activar o desactivar un proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del proveedor actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/cambiar-status/{id}")
    public ResponseEntity<String> actualizarProveedorStatus(@PathVariable Long id,@RequestBody Proveedor proveedor){
        String mensajeProveedorActualizar = proveedorService.actualizarEstadoProveedor(id, proveedor);
        return ResponseEntity.ok(mensajeProveedorActualizar);
    }
}
