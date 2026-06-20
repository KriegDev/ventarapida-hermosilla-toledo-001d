package com.punto_de_venta.service_auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.punto_de_venta.service_auth.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
