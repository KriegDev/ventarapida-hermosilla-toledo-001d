package com.punto_de_venta.service_notificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionesController {

    @Autowired
    private NotificacionesService notificacionesService;

    @GetMapping
    public List<Notificaciones> listarTodas() {
        return notificacionesService.listarNotificaciones();
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaciente(@PathVariable Long id, @Valid @RequestBody Notificaciones notificaciones) {
        try {
            Notificaciones actualizada = notificacionesService.actualizarNotificaciones(id, notificaciones);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar notificación: " + e.getMessage());
        }
    }

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