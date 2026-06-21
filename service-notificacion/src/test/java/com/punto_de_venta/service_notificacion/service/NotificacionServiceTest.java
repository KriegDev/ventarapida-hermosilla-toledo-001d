package com.punto_de_venta.service_notificacion.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import com.punto_de_venta.service_notificacion.model.Notificaciones;
import com.punto_de_venta.service_notificacion.repository.NotificacionesRepository;
 
import java.time.LocalDate;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
@ExtendWith(MockitoExtension.class) // Utilizacion de Mockito para simular objetos
class NotificacionesServiceTest {
 
    @Mock
    private NotificacionesRepository notificacionesRepository; // Simulamos el repositorio
 
    @InjectMocks
    private NotificacionesService notificacionesService;
 
    @Test
    @DisplayName("Debería guardar una notificación correctamente")
    void guardarNotificacionTest() {
        Notificaciones notificacion = new Notificaciones();
        notificacion.setEmail("cachupin15@gmail.com");
        notificacion.setTipo("Compra registrada");
        notificacion.setStatus("Enviada");
        notificacion.setFechaEnvio(LocalDate.of(2026, 7, 2));
 
        when(notificacionesRepository.save(any(Notificaciones.class))).thenAnswer(invocation -> {
            Notificaciones n = invocation.getArgument(0);
            n.setId(1L); // le asignamos manualmente el 1
            return n;
        });
        Notificaciones resultado = notificacionesService.crearNotificacion(notificacion);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("cachupin15@gmail.com", resultado.getEmail());
        verify(notificacionesRepository, times(1)).save(notificacion);
    }
 
    @Test
    @DisplayName("Debería buscar una notificación por ID correctamente")
    void buscarNotificacionPorIdTest() {
        Notificaciones notificacion = new Notificaciones();
        notificacion.setId(1L);
        notificacion.setEmail("cachupin15@gmail.com");
        notificacion.setTipo("Compra registrada");
        notificacion.setStatus("Enviada");
        notificacion.setFechaEnvio(LocalDate.of(2026, 7, 2));
        when(notificacionesRepository.findById(1L)).thenReturn(Optional.of(notificacion));
 
        Notificaciones resultado = notificacionesService.buscarNotificacionesId(1L);
 
        assertNotNull(resultado);
        assertEquals("cachupin15@gmail.com", resultado.getEmail());
        verify(notificacionesRepository, times(1)).findById(1L);
    }
 
    @Test
    @DisplayName("Debería lanzar una excepción si la notificación no existe")
    void buscarNotificacionPorIdNoExisteTest() {
        when(notificacionesRepository.findById(99L)).thenReturn(Optional.empty());
 
        assertThrows(RuntimeException.class, () -> {
            notificacionesService.buscarNotificacionesId(99L);
        });
 
        verify(notificacionesRepository, times(1)).findById(99L);
    }
 
    @Test
    @DisplayName("Debería eliminar una notificación correctamente")
    void eliminarNotificacionTest() {
        Notificaciones notificacion = new Notificaciones();
        notificacion.setId(1L);
        notificacion.setEmail("cachupin15@gmail.com");
 
        when(notificacionesRepository.findById(1L)).thenReturn(Optional.of(notificacion));
        
        notificacionesService.eliminarNotificaciones(1L);
 
        verify(notificacionesRepository, times(1)).delete(notificacion);
    }
}