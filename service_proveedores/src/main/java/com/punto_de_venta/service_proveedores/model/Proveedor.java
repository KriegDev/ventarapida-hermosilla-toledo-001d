package com.punto_de_venta.service_proveedores.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
@Entity
@Table(name = "proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa a un proveedor registrado en el sistema de punto de venta")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del proveedor", example = "1")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "No puede contener espacios vacios ni nulos")
    @Schema(description = "Nombre de la compañía proveedora", example = "Distribuidora Central")
    private String compañia;

    @Column(name= "nombre_contacto",nullable = false)
    @NotBlank(message = "No puede contener espacios vacios ni nulos")
    @Schema(description = "Nombre de la compañía proveedora", example = "Distribuidora Central")
    private String nombreContacto;

    @jakarta.validation.constraints.Email
    @NotBlank
    @Schema(description = "Correo electrónico de contacto del proveedor", example = "contacto@proveedor.cl")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    @Schema(description = "Indica si el proveedor está activo", example = "true")
    private Boolean activo;

    @JsonIgnore
    @OneToMany(mappedBy = "proveedor")
    @Schema(description = "Órdenes de compra asociadas al proveedor")
    private List<OrdenCompra> ordenesCompra;
}
