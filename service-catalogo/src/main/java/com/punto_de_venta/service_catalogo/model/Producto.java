package com.punto_de_venta.service_catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "No puede tener nulos ni espacios en blanco")
    @Column(unique = true,nullable=false)
    private String sku;


    @NotBlank(message = "No puede tener nulos ni espacios en blanco")
    @Size(max = 25,message = "El máximo es de 25")
    @Column(nullable=false, length = 25)
    private String nombre;


    @NotBlank(message = "No puede tener nulos ni espacios en blanco")
    @Size(max = 255, message = "El máximo es de 255")
    @Column(nullable = false, length = 255)
    private String descripcion;


    @Min(value = 0, message = "No debe ser menor a 0")
    @NotNull(message = "No puede contener espacios vacios") 
    @Column(name = "precio_base", nullable = false)
    private Long precioBase;

    @NotNull(message = "No puede contener espacios vacios")
    @Column(nullable = false)
    private Boolean granel;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}
