package com.punto_de_venta.service_notificacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.punto_de_venta.service_notificacion.model.Notificaciones;
import com.punto_de_venta.service_notificacion.repository.NotificacionesRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificacionesService {
    @Autowired
    NotificacionesRepository notificacionesRepository;

    @Transactional
    public Notificaciones crearNotificacion(Notificaciones notificacion) {
        return notificacionesRepository.save(notificacion);
    }

    public List<Notificaciones> listarNotificaciones() {
        return notificacionesRepository.findAll();
    }

    public Notificaciones buscarNotificacionesId(Long id) {
        return notificacionesRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La notificación no existe"));
    }

    @Transactional
    public Notificaciones actualizarNotificaciones(Long id, Notificaciones notificacion) {
        Notificaciones notificacionActualizar = notificacionesRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La notificación no existe"));
        notificacionActualizar.setEmail(notificacion.getEmail());
        notificacionActualizar.setTipo(notificacion.getTipo());
        notificacionActualizar.setStatus(notificacion.getStatus());
        notificacionActualizar.setFechaEnvio(notificacion.getFechaEnvio());
        return notificacionesRepository.save(notificacionActualizar);
    }

    @Transactional
    public void eliminarNotificaciones(Long id) {
        Notificaciones notificacionEliminar = notificacionesRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("La notificación no existe"));
        notificacionesRepository.delete(notificacionEliminar);
    }
}
