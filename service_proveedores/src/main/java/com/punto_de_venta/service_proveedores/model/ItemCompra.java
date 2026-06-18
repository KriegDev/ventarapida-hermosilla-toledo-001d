package com.punto_de_venta.service_proveedores.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_compra")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un ítem o producto incluido dentro de una orden de compra")
public class ItemCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del ítem de compra", example = "1")
    private Long id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_orden_compra", nullable = false)
    @Schema(description = "Orden de compra asociada al ítem")
    private OrdenCompra ordenCompra;

    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    @Schema(description = "ID del producto comprado, proveniente del microservicio de catálogo", example = "1")
    private Long idProducto;

    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    @DecimalMin(value = "0.1", message = "La cantidad no puede ser menor a 100 gramos")
    @Column(nullable = false)
    @Schema(description = "Cantidad comprada del producto", example = "10.00")
    private BigDecimal cantidad;

    @Column(name = "costo_unitario", nullable = false)
    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    @Schema(description = "Costo unitario del producto comprado", example = "1900")
    private Long costoUnitario;

    @Column(nullable = false)
    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    @Schema(description = "Indica si el producto comprado es a granel", example = "false")
    private Boolean granel;

    @Transient
    @Schema(description = "Datos del producto obtenidos desde el microservicio de catálogo")
    private Object datosProducto;
}
