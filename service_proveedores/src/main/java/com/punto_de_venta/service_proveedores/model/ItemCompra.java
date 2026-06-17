package com.punto_de_venta.service_proveedores.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class ItemCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_orden_compra", nullable = false)
    private OrdenCompra ordenCompra;

    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    private Long idProducto;

    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    @DecimalMin(value = "0.1", message = "La cantidad no puede ser menor a 100 gramos")
    @Column(nullable = false)
    private BigDecimal cantidad;

    @Column(name = "costo_unitario", nullable = false)
    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    private Long costoUnitario;

    @Column(nullable = false)
    @NotNull(message = "No puede contener espacios en blanco ni nulos")
    private Boolean granel;

    @Transient
    private Object datosProducto;
}
