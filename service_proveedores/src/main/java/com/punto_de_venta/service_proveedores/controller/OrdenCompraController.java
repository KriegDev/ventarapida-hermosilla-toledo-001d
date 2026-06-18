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

import com.punto_de_venta.service_proveedores.model.OrdenCompra;
import com.punto_de_venta.service_proveedores.service.OrdenCompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/ordenes-compras")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Gestión de Órdenes de Compra", description = "Endpoints para crear, listar, buscar y actualizar órdenes de compra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @Operation(summary = "Crear orden de compra", description = "Permite registrar una nueva orden de compra asociada a un proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de compra creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping 
    public ResponseEntity<OrdenCompra> crearOrdenCompra(@Valid @RequestBody OrdenCompra ordenCompra){
        return ResponseEntity.ok(ordenCompraService.crearOrden(ordenCompra));
    }

    @Operation(summary = "Listar órdenes de compra", description = "Obtiene la lista completa de órdenes de compra registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen órdenes de compra disponibles")
    })
    @GetMapping 
    public ResponseEntity<List<OrdenCompra>> listarOrdenesCompra(){
        List<OrdenCompra> lista = ordenCompraService.listarOrdenes();
        if(lista.size()>0){
            return ResponseEntity.ok(lista);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Buscar orden de compra por ID", description = "Busca una orden de compra específica mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de compra encontrada correctamente"),
            @ApiResponse(responseCode = "404", description = "Orden de compra no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompra> buscarOrdenCompraId(@PathVariable Long id){
        OrdenCompra ordenCompra = ordenCompraService.buscarOrdenCompraId(id);
        if(ordenCompra!=null){
            return ResponseEntity.ok(ordenCompra);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar estado de orden de compra", description = "Actualiza el estado de una orden. Si pasa a RECIBIDA, se genera la entrada de inventario correspondiente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado de la orden actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Orden de compra no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/cambiar-status/{id}")
    public ResponseEntity<OrdenCompra> actualizarStatusOrdenCompra(@PathVariable Long id,@RequestBody OrdenCompra ordenCompra){
        OrdenCompra ordenCompraActualizar = ordenCompraService.actualizarStatusOrdenCompra(id, ordenCompra);
        return ResponseEntity.ok(ordenCompraActualizar);
    }


}
