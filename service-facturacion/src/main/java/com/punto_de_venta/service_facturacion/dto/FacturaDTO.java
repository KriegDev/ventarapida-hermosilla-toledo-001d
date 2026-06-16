package com.punto_de_venta.service_facturacion.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FacturaDTO {

    private Long idOrden;
    private Long montoTotal;
}
