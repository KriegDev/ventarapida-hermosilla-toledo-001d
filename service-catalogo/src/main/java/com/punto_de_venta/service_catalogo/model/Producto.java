package com.punto_de_venta.service_catalogo.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa un producto dentro del catálogo del sistema")
public class Producto {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental", example = "1")
    private Long id;

    @NotBlank(message = "No puede tener nulos ni espacios en blanco")
    @Column(unique = true,nullable=false)
    @Schema(description = "SKU del producto", example = "NASG54GAS")
    private String sku;


    @NotBlank(message = "No puede tener nulos ni espacios en blanco")
    @Size(max = 25,message = "El máximo es de 25")
    @Column(nullable=false, length = 25)
    @Schema(description = "Nombre del producto", example = "Arroz Tucapel grano largo")
    private String nombre;


    @NotBlank(message = "No puede tener nulos ni espacios en blanco")
    @Size(max = 255, message = "El máximo es de 255")
    @Column(nullable = false, length = 255)
    @Schema(description = "Descripción del producto", example = "Arroz tucapel grano largo 1kg")
    private String descripcion;


    @Min(value = 0, message = "No debe ser menor a 0")
    @NotNull(message = "No puede contener espacios vacios") 
    @Column(name = "precio_base", nullable = false)
    @Schema(description = "Precio Base del producto", example = "1000")
    private Long precioBase;

    @NotNull(message = "No puede contener espacios vacios")
    @Column(nullable = false)
    @Schema(description = "Para saber si es granel o no el producto", example = "true")
    private Boolean granel;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @Schema(description = "Categoría asociada al producto")
    private Categoria categoria;
}
