package com.punto_de_venta.service_ventas.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orden {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_orden")
    @NotNull(message = "No puede tener nulos ni espacios en blanco")
    private Long numeroOrden;
    
    @Column(name = "id_cliente")
    private Long idCliente;

    @NotBlank(message = "No puede tener nulos ni espacios en blanco")
    private String status;

    @Min(value = 0)
    private Long montoTotal;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detalle> detalles = new ArrayList<>();

    private Long idMetodoPago;


}
