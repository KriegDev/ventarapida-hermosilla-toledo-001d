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
@Entity
@Table(name = "proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "No puede contener espacios vacios ni nulos")
    private String compañia;

    @Column(name= "nombre_contacto",nullable = false)
    @NotBlank(message = "No puede contener espacios vacios ni nulos")
    private String nombreContacto;

    @jakarta.validation.constraints.Email
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    private Boolean activo;

    @JsonIgnore
    @OneToMany(mappedBy = "proveedor")
    private List<OrdenCompra> ordenesCompra;
}
