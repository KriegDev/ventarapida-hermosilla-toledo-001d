package com.punto_de_venta.service_ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {
    private Long idOrden;
    private Long monto;
    private Long IdMetPago;
}
