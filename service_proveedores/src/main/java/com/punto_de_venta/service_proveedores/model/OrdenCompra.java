package com.punto_de_venta.service_proveedores.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El proveedor no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false)
    @NotBlank(message = "No puede contener espacios vacios ni nulos")
    private String status;

    @Column(name = "costo_total", nullable = false)
    private Long costoTotal;

    @Column(name = "fecha_compra", nullable = false)
    @NotNull(message = "La fecha de compra no puede ser nula")
    private LocalDate fechaCompra;


    @OneToMany(mappedBy = "ordenCompra")
    private List<ItemCompra> items;
}
