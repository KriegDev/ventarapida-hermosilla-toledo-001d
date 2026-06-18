package com.punto_de_venta.service_facturacion.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "factura")
@Data @NoArgsConstructor @AllArgsConstructor
@Schema(description = "Modelo que representa los documentos de venta (facturas/boletas)")
public class Factura {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID Autoincremental", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Id de la orden de venta a la que está asociada la boleta")
    private Long idOrden;

    @Column(nullable = false)
    @Schema(description = "Monto subtotal", example = "2590")
    private Long montoSubtotal;

    @Column(nullable = false)
    @Schema(description = "Impuesto según legislación (como el IVA)", example = "492.1")
    private BigDecimal impuesto;

    @Column(nullable = false)
    @Schema(description = "Monto total después de impuestos", example = "3082")
    private Long montoTotal;

    @Schema(description = "Registro de fecha de emisión de la boleta")
    private LocalDateTime fechaEmision;
}
