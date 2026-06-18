package com.punto_de_venta.service_inventario.model;

import jakarta.persistence.Transient;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock")
@Data @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Clase que representa el stock de los productos para control de inventario")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del registro de stock en la base de datos", example = "1")
    private Long id;

    @NotNull(message = "No puede contener espacios en blancos ni nulos")
    @Schema(description = "ID del producto asociado (proveniente del microservicio de Catálogo)", example = "1405")
    private Long idProducto;

    @Column(nullable = false)
    @NotNull(message = "No puede contener espacios en blancos ni nulos")
    @Min(value = 0, message = "Cantidad no debe ser menor a cero")  
    @Schema(description = "Cantidad actual disponible del producto para la venta", example = "150")  
    private BigDecimal cantidad;

    @NotNull(message = "No puede contener espacios en blancos ni nulos")    
    @Min(value = 0, message = "No debe ser menor a 0")
    @Column(name = "stock_minimo", nullable = false)
    @Schema(description = "Umbral mínimo permitido antes de disparar una alerta de reposición de inventario", example = "15")
    private BigDecimal stockMinimo;

    @Transient
    @Schema(description = "Objeto temporal (no persistido en BD) para anexar la información externa del producto obtenida de Producto")
    private Object datosProducto;

}
