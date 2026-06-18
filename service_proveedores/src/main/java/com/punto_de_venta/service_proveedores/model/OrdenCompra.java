package com.punto_de_venta.service_proveedores.model;

import java.time.LocalDate;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orden_compra")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una orden de compra realizada a un proveedor")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Entidad que representa una orden de compra realizada a un proveedor")
    private Long id;

    @NotNull(message = "El proveedor no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    @Schema(description = "Proveedor asociado a la orden de compra")
    private Proveedor proveedor;

    @Column(nullable = false)
    @NotBlank(message = "No puede contener espacios vacios ni nulos")
    @Schema(description = "Estado actual de la orden de compra", example = "PENDIENTE")
    private String status;

    @Column(name = "costo_total", nullable = false)
    @Schema(description = "Costo total acumulado de la orden de compra", example = "38000")
    private Long costoTotal;

    @Column(name = "fecha_compra", nullable = false)
    @NotNull(message = "La fecha de compra no puede ser nula")
    @Schema(description = "Fecha en que se registra la orden de compra", example = "2026-06-17")
    private LocalDate fechaCompra;


    @OneToMany(mappedBy = "ordenCompra")
    @Schema(description = "Lista de ítems asociados a la orden de compra")
    private List<ItemCompra> items;
}
