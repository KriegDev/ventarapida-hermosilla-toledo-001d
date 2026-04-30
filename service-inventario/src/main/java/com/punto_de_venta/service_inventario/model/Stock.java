package com.punto_de_venta.service_inventario.model;

import jakarta.persistence.Transient;
import java.math.BigDecimal;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long idProducto;

    @Column(nullable = false)
    @NotNull
    @Min(value = 0, message = "Cantidad no debe ser menor a cero")    
    private BigDecimal cantidad;

    @NotNull    
    @Min(value = 0, message = "No debe ser menor a 0")
    @Column(name = "stock_minimo", nullable = false)
    private BigDecimal stockMinimo;

    @Transient
    private Object datosProducto;

}
