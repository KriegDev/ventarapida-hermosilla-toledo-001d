package com.punto_de_venta.service_notificacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.punto_de_venta.service_notificacion.model.Notificaciones;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {
    
}
