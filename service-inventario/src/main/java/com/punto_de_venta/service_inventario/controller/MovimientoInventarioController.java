package com.punto_de_venta.service_inventario.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_inventario.model.MovimientoInventario;
import com.punto_de_venta.service_inventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/movimiento-inventario")
@Tag(name = "Historial de Movimientos", description = "Endpoints para registrar y consultar entradas y salidas de inventario")
@CrossOrigin(origins = "*")
public class MovimientoInventarioController {

    @Autowired
    private InventarioService is; 
    
    @Operation(summary = "Registrar nuevo movimiento", description = "Crea un registro de entrada, salida o ajuste de inventario, asociándolo al ID de un registro de stock específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro procesado y creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación en el formato del JSON enviado", content = @Content)
    })
    @PostMapping("/nuevo/{id}")
    public ResponseEntity<MovimientoInventario> crear(@Valid @RequestBody MovimientoInventario mov, @PathVariable Long id){
        return ResponseEntity.ok(is.nuevoMovimiento(id, mov));
    }

    @Operation(summary = "Buscar movimiento por ID", description = "Obtiene los detalles exactos de una transacción de inventario mediante su identificador único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún registro con el parámetro proporcionado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventario> buscarPorId(@PathVariable Long id){
         return is.obtenerMovimientoPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar movimientos por día", description = "Filtra el historial de movimientos de inventario que ocurrieron en una fecha exacta (Formato: YYYY-MM-DD).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping("/buscar/{dia}")
    public ResponseEntity<List<MovimientoInventario>> buscarPorDia(
    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia) {
    
        return is.obtenerMovimientosPorDia(dia)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
}

    @Operation(summary = "Buscar movimientos a partir de una fecha", description = "Obtiene todos los movimientos registrados desde la fecha indicada en adelante (Formato: YYYY-MM-DD).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping("/buscar-desde/{fecha}")
    public ResponseEntity<List<MovimientoInventario>> buscarMovimientosDesde(
    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        return is.obtenerMovimientosDesde(fecha).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Buscar movimientos entre fechas", description = "Obtiene el historial de movimientos registrados dentro de un periodo de tiempo determinado (inicio y fin en formato YYYY-MM-DD).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping("/entre/{ini}/y/{fin}")
    public ResponseEntity<List<MovimientoInventario>> buscarEntreFechas(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ini, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin){
        return is.obtenerMovimientosEntre(ini, fin).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Listar todos los movimientos", description = "Devuelve el historial completo de todas las operaciones (entradas y salidas) de inventario registradas en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping ResponseEntity<List<MovimientoInventario>> listarMovimientos(){
        List<MovimientoInventario> ll = is.listarMovimientos();
        if (ll.size()>0) {
            return ResponseEntity.ok(ll);
        }
        return ResponseEntity.noContent().build();
    }
}
