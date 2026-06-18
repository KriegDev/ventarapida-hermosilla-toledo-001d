package com.punto_de_venta.service_facturacion.controller;

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

import com.punto_de_venta.service_facturacion.dto.FacturaDTO;
import com.punto_de_venta.service_facturacion.model.Factura;
import com.punto_de_venta.service_facturacion.service.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("api/v1/facturas")
@Tag(name = "Gestión de Facturación", description = "Endpoints encargados de emitir y consultar las facturas de las órdenes procesadas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService fts;

    @Operation(summary = "Generar una nueva factura", description = "Emite una nueva factura recibiendo el ID de la orden de venta y el monto total a facturar.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro procesado y creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación en el formato del JSON enviado", content = @Content)
    })
    @PostMapping("/generar")
    public ResponseEntity<Factura> generarFactura(@RequestBody FacturaDTO peticion){
        Factura nuevaFactura = fts.generarFactura(peticion.getIdOrden(), peticion.getMontoTotal());
        return ResponseEntity.ok(nuevaFactura);
    }

    @Operation(summary = "Listar todas las facturas", description = "Obtiene un historial completo de todas las facturas registradas en la base de datos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<List<Factura>> listarFacturas(){
        List<Factura> listaFacturas = fts.listarFacturas();
        if (listaFacturas.size()>0) {
            return ResponseEntity.ok(listaFacturas);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar facturas cronológicamente", description = "Obtiene el listado de facturas ordenadas por su fecha de emisión.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping("/cronologico")
    public ResponseEntity<List<Factura>> listarPorFecha(){
        List<Factura> listaFacturas = fts.listarFacturasPorFecha();
        if (listaFacturas.size()>0) {
            return ResponseEntity.ok(listaFacturas);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar factura por ID", description = "Consulta los detalles exactos de una factura en específico a través de su identificador único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún registro con el parámetro proporcionado", content = @Content)
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Factura> buscarPorId(@PathVariable Long id){
        Factura resultado = fts.buscarFacturaPorId(id);
        if (resultado!=null) {
            return ResponseEntity.ok(resultado);
        }
        return ResponseEntity.notFound().build();
    }
}
