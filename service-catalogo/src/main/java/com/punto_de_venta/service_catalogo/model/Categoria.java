package com.punto_de_venta.service_catalogo.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categoria")
@Schema(description = "Entidad que representa un producto dentro del catálogo del sistema de punto de venta")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental", example = "1")
    private Long id;

    @NotBlank(message = "No puede contener espacios en blanco ni nulos")
    @Size(max = 25, message = "Excede el largo máximo de 25")
    @Column(nullable = false, length = 25)
    @Schema(description = "Nombre de la categoría", example = "Abarrotes")
    private String nombre;    
}
