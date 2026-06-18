package com.punto_de_venta.service_ventas.model;


import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle")
@Data @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad que representa un ítem o detalle específico dentro de una orden de compra")
public class Detalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del detalle de la orden", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden")
    @JsonIgnore
    @Schema(description = "Referencia a la orden principal a la que pertenece este detalle (Oculto en las respuestas JSON)", hidden = true)
    private Orden orden;

    @NotNull(message = "No puede tener nulos ni espacios en blanco")
    @Column(name = "id_producto")
    @Schema(description = "Identificador del producto asociado (proveniente del microservicio de Catálogo)", example = "105")
    private Long idProducto;

    @Column(nullable = false)
    @Schema(description = "Cantidad del producto solicitada en la orden", example = "2.0")
    private BigDecimal cantidad;

    @Column(nullable = false)
    @Schema(description = "Precio unitario del producto en el momento de realizar la compra", example = "15000")
    private Long precioUnitario;

    @Transient
    @Schema(description = "Objeto temporal (no persistido en BD) para anexar la información externa del producto obtenida del Catálogo")
    private Object datosProducto;
}
