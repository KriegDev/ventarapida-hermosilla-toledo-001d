package com.punto_de_venta.service_ventas.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Data @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad que representa una orden de compra principal, agrupando los productos solicitados y el estado del pago")
public class Orden {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la orden en la base de datos", example = "1")
    private Long id;

    @Column(name = "numero_orden")
    @NotNull(message = "No puede tener nulos ni espacios en blanco")
    @Schema(description = "Número correlativo o identificador comercial visible de la orden", example = "10045")
    private Long numeroOrden;
    
    @Column(name = "id_cliente")
    @Schema(description = "Identificador del cliente que está realizando la compra", example = "789")
    private Long idCliente;

    @NotBlank(message = "No puede tener nulos ni espacios en blanco")
    @Schema(description = "Estado actual del flujo de la orden (ej. PENDIENTE, PAGADO, CANCELADO)", example = "PENDIENTE")
    private String status;

    @Min(value = 0)
    @Schema(description = "Costo total a cobrar por la orden, consolidando todos los detalles", example = "45000")
    private Long montoTotal;

    @Column(name = "fecha_venta")
    @Schema(description = "Fecha en la que se generó y registró la venta", example = "2026-06-18")
    private LocalDate fechaVenta;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de ítems o productos específicos que componen esta orden")
    private List<Detalle> detalles = new ArrayList<>();

    @Schema(description = "Identificador del método de pago seleccionado (1: Tarjeta Crédito, 2: Tarjeta Débito, 3: Débito Directo, 4: Flow)", example = "1")
    private Long idMetodoPago;


}
