package com.punto_de_venta.service_auth.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.punto_de_venta.service_auth.model.Usuario;
import com.punto_de_venta.service_auth.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UsuarioRepository usuarioRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String registrar(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioRepository.save(usuario);
        return "Usuario Registrado";
    }

    public String login(String username, String password) {
        System.out.println("Intentando login para: " + username);

        Usuario user = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        System.out.println("Usuario encontrado en BD: " + user.getNombreUsuario());

        if (passwordEncoder.matches(password, user.getContrasena())) {
            return jwtService.generarToken(username, List.of());
        }

        throw new RuntimeException("Credenciales inválidas");
    }
}