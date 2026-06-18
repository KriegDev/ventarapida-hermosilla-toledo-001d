package com.punto_de_venta.service_ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_ventas.model.Orden;
import com.punto_de_venta.service_ventas.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("api/v1/ordenes")
@Tag(name = "Gestión de Órdenes de Venta", description = "Endpoints principales para crear transacciones, consultar historial y calcular ingresos")
@CrossOrigin(origins = "*")
public class OrdenController {
    @Autowired
    private VentaService vs;

    @Operation(summary = "Procesar una nueva venta (Crear Orden)", description = "Genera una nueva orden de compra. Este endpoint se comunica internamente con Inventario (para reservar stock) y con Pagos (para procesar el cobro).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro procesado y creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación en el formato del JSON enviado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestBody Orden orden){
        Orden nuevaOrden = vs.procesarVenta(orden);
        return ResponseEntity.ok(nuevaOrden);
    }

    @Operation(summary = "Buscar orden por ID interno", description = "Obtiene toda la información y detalles de los productos de una orden utilizando su identificador único de base de datos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún registro con el parámetro proporcionado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Orden> buscarPorId(@PathVariable Long id){
        Orden orden = vs.buscarPorId(id);
        if (orden!=null) {
            return ResponseEntity.ok(orden);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    @Operation(summary = "Buscar orden por Número de comprobante", description = "Recupera una orden utilizando su número correlativo comercial (numero_orden) visible para el cliente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún registro con el parámetro proporcionado", content = @Content)
    })
    @GetMapping("/numero/{nro}")
    public ResponseEntity<Orden> buscarPorNumero(@PathVariable Long nro){
        Orden orden = vs.buscarPorNumeroOrden(nro);
        if (orden!=null) {
            return ResponseEntity.ok(orden);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Filtrar órdenes por estado", description = "Obtiene una lista de órdenes según la etapa en la que se encuentren (ej. PENDIENTE, PAGADO, CANCELADO).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Orden>> listarPorStatus(@PathVariable String status){
        List<Orden> listaOrdenes = vs.listarPorStatus(status);
        if (listaOrdenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaOrdenes);
    }

    @Operation(summary = "Listar todas las órdenes", description = "Devuelve el registro histórico completo de todas las ventas que se han intentado o procesado en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Orden>> listarOrdenes(){
        List<Orden> listaOrdenes = vs.listarOrdenes();
        if (listaOrdenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaOrdenes);
    }

    @Operation(summary = "Calcular ingresos totales", description = "Suma dinámicamente el monto total de absolutamente todas las órdenes que se encuentran exitosamente en estado 'PAGADO'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteo calculado con éxito (devuelve el valor numérico)")
    })
    @GetMapping("/total-recaudado")
    public ResponseEntity<Long> obtenerTotalRecaudado(){
        Long total = vs.listarOrdenes().stream()
        .filter(o -> "PAGADO".equalsIgnoreCase(o.getStatus()))
        .mapToLong(o -> o.getMontoTotal()).sum();

        return ResponseEntity.ok(total);
    }
}
