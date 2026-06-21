package com.punto_de_venta.service_notificacion.repository;

import com.punto_de_venta.service_notificacion.model.Notificaciones;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
 
import static org.junit.jupiter.api.Assertions.*;
 
@DataJpaTest
class NotificacionesRepositoryTest {
 
    @Autowired
    private NotificacionesRepository notificacionesRepository;
 
    @Test
    void guardarYBuscarNotificacionTest() {
        Notificaciones notificacion = new Notificaciones();
        notificacion.setEmail("cachupin15@gmail.com");
        notificacion.setTipo("Compra registrada");
        notificacion.setStatus("Enviada");
        notificacion.setFechaEnvio(LocalDate.of(2026, 7, 2));
 
        Notificaciones guardada = notificacionesRepository.save(notificacion);

        Notificaciones encontrada = notificacionesRepository.findById(guardada.getId()).orElse(null);
 
        assertNotNull(encontrada);
        assertEquals("cachupin15@gmail.com", encontrada.getEmail());
        assertEquals("Compra registrada", encontrada.getTipo());
        assertEquals("Enviada", encontrada.getStatus());
    }
 
    @Test
    void eliminarNotificacionTest() {
        Notificaciones notificacion = new Notificaciones();
        notificacion.setEmail("eliminar@gmail.com");
        notificacion.setTipo("Pago confirmado");
        notificacion.setStatus("Pendiente");
        notificacion.setFechaEnvio(LocalDate.of(2026, 8, 1));
 
        Notificaciones guardada = notificacionesRepository.save(notificacion);
        Long id = guardada.getId();
 
        notificacionesRepository.delete(guardada);
 
        assertTrue(notificacionesRepository.findById(id).isEmpty());
    }
}
