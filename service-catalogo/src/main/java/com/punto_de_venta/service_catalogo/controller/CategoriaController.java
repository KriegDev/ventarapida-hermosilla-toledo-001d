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
import com.punto_de_venta.service_catalogo.model.Categoria;
import com.punto_de_venta.service_catalogo.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Gestión de Categorías", description = "Endpoints para administrar las categorías del catálogo")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Crear categoría", description = "Permite registrar una nueva categoría en el catálogo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud")
})
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody Categoria categoria){
        return ResponseEntity.ok(categoriaService.crearCategoria(categoria));
    }

    @Operation(summary = "Listar categorías", description = "Obtiene la lista completa de categorías registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @ApiResponse(responseCode = "204", description = "No existen registros disponibles")
})
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias(){
        List<Categoria> categorias= categoriaService.listarCategorias();
        if(categorias.size()>0){
            return ResponseEntity.ok(categorias);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Buscar categoría por ID", description = "Busca una categoría específica mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaId(@PathVariable Long id){
        Categoria categoria = categoriaService.buscarCategoriaId(id);
        if (categoria!=null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar categoría", description = "Permite modificar los datos de una categoría existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos enviados en la solicitud"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @Valid @RequestBody Categoria categoria){
        Categoria categoriaActualizar = categoriaService.actualizarCategoria(id, categoria);
        return ResponseEntity.ok(categoriaActualizar);
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría del catálogo según su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id){
        String categoriaEliminar = categoriaService.eliminarCategoria(id);
        if (categoriaEliminar!=null) {
            return ResponseEntity.ok(categoriaEliminar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
