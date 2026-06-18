package com.punto_de_venta.service_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.punto_de_venta.service_inventario.model.Stock;
import com.punto_de_venta.service_inventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/stock")
@Tag(name = "Gestión de Inventario y Stock", description = "Endpoints para consultar la disponibilidad de productos, establecer límites y visualizar alertas de reposición")
@CrossOrigin(origins = "*")
public class StockController {
    @Autowired
    private InventarioService is;

    @Operation(summary = "Obtener stock por ID", description = "Recupera la información exacta de disponibilidad y límites de un registro de stock mediante su identificador único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún registro con el parámetro proporcionado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Stock> obtenerStockId(@PathVariable Long id){
        return ResponseEntity.ok(is.obtenerStockId(id));
    }

    @Operation(summary = "Listar productos con bajo stock", description = "Devuelve una lista con todos los registros de inventario cuya cantidad actual ha caído por debajo del nivel de stock mínimo permitido.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping("/bajo-stock")
    public ResponseEntity<List<Stock>> alertaBajoStock(){
        List<Stock> ll = is.alertaBajoStock();
        if (ll.size()>0) {
            return ResponseEntity.ok(ll);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar nivel de stock mínimo", description = "Permite modificar el umbral mínimo (stock_minimo) de un producto. Recibe el ID en la ruta y el nuevo valor en el cuerpo de la petición.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso modificado con éxito (devuelve la entidad con los cambios aplicados)"),
        @ApiResponse(responseCode = "400", description = "Error de validación o datos mal estructurados en el cuerpo de la petición", content = @Content),
        @ApiResponse(responseCode = "404", description = "No se encontró ningún registro con el ID especificado", content = @Content)
    })
    @PatchMapping("stock-minimo/{id}")
    public ResponseEntity<Stock> actualizarStockMinimo(@PathVariable Long id, @RequestBody Stock stock){
        is.actualizarStockMinimo(id, stock.getStockMinimo());
        return ResponseEntity.ok(is.obtenerStockId(id));
    }

    @Operation(summary = "Listar inventario completo", description = "Obtiene un consolidado global con los niveles de stock de todos los productos registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado recuperado con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay registros disponibles para mostrar", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Stock>> listarStock(){
        List<Stock> stock= is.listarStock();
        if(stock.size()>0){
            return ResponseEntity.ok(stock);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Contador de alertas de inventario", description = "Devuelve únicamente el número total (cantidad numérica) de productos que necesitan reposición urgente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteo calculado con éxito (devuelve el valor numérico)")
    })
    @GetMapping("/alertas-count")
    public ResponseEntity<Long> contarAlertasBajoStock(){
        Long cantidad = (long) is.alertaBajoStock().size();
        return ResponseEntity.ok(cantidad);
    }
}
