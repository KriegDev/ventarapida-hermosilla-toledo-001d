package com.punto_de_venta.service_notificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.punto_de_venta.service_notificacion.model.Notificaciones;
import com.punto_de_venta.service_notificacion.service.NotificacionesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Notificaciones", description = "Notificaciones registradas en el sistema, contiene datos relevantes como estado y fecha de envío")
@CrossOrigin(origins = "*")
public class NotificacionesController {

    @Autowired
    private NotificacionesService notificacionesService;
    @Operation(summary = "Mostrar todas las notificaciones", description = "Obtiene todas las notificaciones registradas en el sistema de venta")
    @GetMapping
    public List<Notificaciones> listarTodas() {
        return notificacionesService.listarNotificaciones();
    }
    @Operation(summary = "Buscar por ID", description = "Si el ID existe, muestra la notificación correspondiente junto a sus datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación encontrada."),
        @ApiResponse(responseCode = "404", description = "Notificación no existe.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Notificaciones notificacion = notificacionesService.buscarNotificacionesId(id);
            return ResponseEntity.ok(notificacion);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al buscar notificación: " + e.getMessage());
        }
    }
    @Operation(summary = "Crear notificación", description = "Crea una nueva notificación dentro del sistema de venta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notificación creada correctamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incorrectos.")
    })
    @PostMapping
    public ResponseEntity<?> crearNotificacion(@Valid @RequestBody Notificaciones notificaciones) {
        try {
            Notificaciones saved = notificacionesService.crearNotificacion(notificaciones);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear notificación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }
    @Operation(summary = "Actualizar notificación", description = "")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación actualizada correctamente."),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incorrectos.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarNotificaciones(@Valid @PathVariable Long id, @Valid @RequestBody Notificaciones notificaciones) {
        try {
            Notificaciones actualizada = notificacionesService.actualizarNotificaciones(id, notificaciones);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar notificación: " + e.getMessage());
        }
    }
    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notificación eliminada correctamente, sin contenido en la respuesta."),
        @ApiResponse(responseCode = "404", description = "La notificación no existe.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarNotificaciones(@PathVariable Long id) {
        try {
            notificacionesService.eliminarNotificaciones(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al eliminar notificación: " + e.getMessage());
        }
    }
}