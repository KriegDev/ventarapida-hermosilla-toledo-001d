package com.punto_de_venta.service_notificacion;

import com.punto_de_venta.service_notificacion.model.Notificaciones;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import java.time.LocalDate;
 
import static org.junit.jupiter.api.Assertions.*;
 
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class NotificacionesIntegrationTest {
 
    @Autowired
    private TestRestTemplate restTemplate;
 
    @Test
    void flujoCompletoCrearNotificacionTest() {
        Notificaciones nuevaNotificacion = new Notificaciones();
        nuevaNotificacion.setEmail("cachupin15@gmail.com");
        nuevaNotificacion.setTipo("Compra registrada");
        nuevaNotificacion.setStatus("Enviada");
        nuevaNotificacion.setFechaEnvio(LocalDate.of(2026, 7, 2));
 
        ResponseEntity<Notificaciones> respuesta = restTemplate.postForEntity(
                "/api/v1/notificaciones", nuevaNotificacion, Notificaciones.class);
 
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertNotNull(respuesta.getBody().getId());
        assertEquals("cachupin15@gmail.com", respuesta.getBody().getEmail());
    }
 
    @Test
    void flujoCompletoEliminarNotificacionTest() {
        Notificaciones notificacion = new Notificaciones();
        notificacion.setEmail("eliminar@gmail.com");
        notificacion.setTipo("Pago confirmado");
        notificacion.setStatus("Pendiente");
        notificacion.setFechaEnvio(LocalDate.of(2026, 8, 1));
 
        ResponseEntity<Notificaciones> creada = restTemplate.postForEntity(
                "/api/v1/notificaciones", notificacion, Notificaciones.class);
        Long id = creada.getBody().getId();

        restTemplate.delete("/api/v1/notificaciones/" + id);
 
        //la notificación ya no debería existir
        ResponseEntity<String> respuesta = restTemplate.getForEntity(
                "/api/v1/notificaciones/" + id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}
