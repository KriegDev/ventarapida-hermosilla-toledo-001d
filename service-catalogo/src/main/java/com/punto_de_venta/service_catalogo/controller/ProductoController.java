package com.punto_de_venta.service_catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_catalogo.exception.ErrorResponse;
import com.punto_de_venta.service_catalogo.model.Producto;
import com.punto_de_venta.service_catalogo.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Gestión de Productos", description = "Endpoints para administrar los productos del catálogo")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Crear producto", description = "Permite registrar un nuevo producto en el catálogo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud")
})
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto){
        return ResponseEntity.ok(productoService.crearProducto(producto));
    }

    @Operation(summary = "Listar productos", description = "Obtiene la lista completa de productos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @ApiResponse(responseCode = "204", description = "No existen registros disponibles")
})
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        List<Producto> productos= productoService.listarProductos();
        if(productos.size()>0){
            return ResponseEntity.ok(productos);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Buscar producto por ID", description = "Busca un producto específico mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProductoId(@PathVariable Long id){
        Producto producto = productoService.buscarProductoId(id);
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Buscar producto por SKU", description = "Busca un producto específico mediante su SKU")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SKU encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "SKU no encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
    @GetMapping("/sku/{sku}")
    public ResponseEntity<Producto> buscarProductSku(@PathVariable String sku){
        Producto producto = productoService.buscarProductoSku(sku);
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Actualizar producto", description = "Permite modificar los datos de un producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id,@Valid  @RequestBody Producto producto){
        Producto productoActualizar = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizar);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto del catálogo según su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id){
        String productoEliminar = productoService.eliminarProducto(id);
        return ResponseEntity.ok(productoEliminar);
    }

   @Operation(summary = "Verificar existencia de producto", description = "Verifica si un producto existe mediante su ID. Este endpoint es utilizado por otros microservicios.")
   @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resultado de existencia obtenido correctamente")
})
   @GetMapping("/existe/{id}") 
   public ResponseEntity<Boolean> existe(@PathVariable Long id){
        Boolean existe = productoService.existe(id);
        return ResponseEntity.ok(existe);
    }



}
