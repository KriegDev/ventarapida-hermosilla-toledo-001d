package com.punto_de_venta.service_auth.model;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "rol")
@EqualsAndHashCode(onlyExplicitlyIncluded=true)

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autoincremental", example = "1")
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "nombre_usuario", unique = true, nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 15, message = "El nombre debe tener entre 3 y 15 caracteres")
    @Schema(description = "Nombre de usuario", example = "Cachupin15", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreUsuario;
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(name = "contrasena", nullable = false)
    private String contrasena;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> rol = new HashSet<>();
}
