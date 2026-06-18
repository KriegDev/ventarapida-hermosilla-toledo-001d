package com.punto_de_venta.service_proveedores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_proveedores.model.ItemCompra;
import com.punto_de_venta.service_proveedores.service.ItemCompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/items-compras")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Gestión de Ítems de Compra", description = "Endpoints para crear, listar y buscar ítems de compra")
public class ItemCompraController {

    @Autowired
    private ItemCompraService itemCompraService;

    @Operation(summary = "Crear ítem de compra", description = "Permite registrar un producto dentro de una orden de compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem de compra creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Orden de compra no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping  
    public ResponseEntity<ItemCompra> crearItemCompra(@Valid @RequestBody ItemCompra itemCompra){
        return ResponseEntity.ok(itemCompraService.crearItemCompra(itemCompra));
    }

    @Operation(summary = "Listar ítems de compra", description = "Obtiene la lista completa de ítems de compra registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "204", description = "No existen ítems de compra disponibles")
    })
    @GetMapping 
    public ResponseEntity<List<ItemCompra>> listarItemsCompra(){
        List<ItemCompra> lista = itemCompraService.listarItemsCompra();
        if(lista.size()>0){
            return ResponseEntity.ok(lista);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Buscar ítem de compra por ID", description = "Busca un ítem de compra específico mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem de compra encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Ítem de compra no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemCompra> buscarItemCompraId(@PathVariable Long id){
        ItemCompra item = itemCompraService.buscarItemCompraId(id);
        if(item!=null){
            return ResponseEntity.ok(item);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
