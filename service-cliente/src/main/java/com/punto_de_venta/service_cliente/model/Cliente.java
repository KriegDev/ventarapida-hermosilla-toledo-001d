package com.punto_de_venta.service_cliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false, unique = true)
    private Long run;

    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false)
    private Character dvrun;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false, name = "pnombre")
    private String pNombre;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false,name = "snombre")
    private String sNombre;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false,name = "apmaterno")
    private String apMaterno;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false,name = "appaterno")
    private String apPaterno;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @jakarta.validation.constraints.Email(message = "Formato de correo inválido")
    @Column(nullable = false)
    private String correo;

    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private Long telefono;

    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    private Boolean activo;
    
}
