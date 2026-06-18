package com.punto_de_venta.service_cliente.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa a un cliente registrado en el sistema de punto de venta")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del cliente", example = "1")
    private Long id;

    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false, unique = true)
    @Schema(description = "RUN del cliente sin dígito verificador", example = "12345678")
    private Long run;

    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false)
    @Schema(description = "Dígito verificador del RUN del cliente", example = "9")
    private Character dvrun;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false, name = "pnombre")
    @Schema(description = "Primer nombre del cliente", example = "Carolina")
    private String pNombre;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false,name = "snombre")
    @Schema(description = "Segundo nombre del cliente", example = "Andrea")
    private String sNombre;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false,name = "apmaterno")
     @Schema(description = "Apellido materno del cliente", example = "Baeza")
    private String apMaterno;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @Column(nullable = false,name = "appaterno")
    @Schema(description = "Apellido paterno del cliente", example = "Hermosilla")
    private String apPaterno;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @jakarta.validation.constraints.Email(message = "Formato de correo inválido")
    @Column(nullable = false)
    @Schema(description = "Correo electrónico del cliente", example = "cliente@correo.cl")
    private String correo;

    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    @Schema(description = "Número telefónico del cliente", example = "987654321")
    private Long telefono;

    @NotNull(message = "No puede estar vacio")
    @Column(nullable = false)
    @Schema(description = "Indica si el cliente se encuentra activo en el sistema", example = "true")
    private Boolean activo;
    
}
