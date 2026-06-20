package com.punto_de_venta.service_facturacion.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.punto_de_venta.service_facturacion.model.Factura;
import com.punto_de_venta.service_facturacion.repository.FacturaRepository;

@ExtendWith(MockitoExtension.class)
public class FacturaServiceTest {

    @Mock
    private FacturaRepository ftr;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private FacturaService facturaService;

    @Test
    @DisplayName("Debería calcular el subtotal e impuestos correctamente y guardar la factura")
    void generarFactura(){
        Long idOrdenSimulada = 101L;
        Long montoTotalSimulado = 1190L;

        when(ftr.save(any(Factura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Factura resultado = facturaService.generarFactura(idOrdenSimulada, montoTotalSimulado);

        assertNotNull(resultado, "La factura generada no debe ser nula");
        assertEquals(idOrdenSimulada, resultado.getIdOrden());

        assertEquals(1000L, resultado.getMontoSubtotal(), "El monto subtotal calculado es incorrecto");

        assertEquals(new BigDecimal("190"), resultado.getImpuesto(), "El impuesto calculado es incorrecto");
        assertEquals(montoTotalSimulado, resultado.getMontoTotal(), "El monto total debe mantenerse igual");
        assertNotNull(resultado.getFechaEmision(), "La factura debe tener fecha de emisión");

        verify(ftr, times(1)).save(any(Factura.class));
    }

    @Test
    @DisplayName("Debería listar todas las facturas")
    void listarFacturasTest(){
        Factura f1 = new Factura();
        Factura f2 = new Factura();
        List<Factura> listaSimulada = List.of(f1, f2);

        when(ftr.findAll()).thenReturn(listaSimulada);

        List<Factura> resultado = facturaService.listarFacturas();

        assertEquals(2, resultado.size());
        verify(ftr, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería listar facturas ordenadas por fecha")
    void listarFacturasPorFechaTest(){
        List<Factura> listaSimulada = List.of(new Factura(), new Factura());
        when(ftr.findAllByOrderByFechaEmisionAsc()).thenReturn(listaSimulada);

        List<Factura> resultado = facturaService.listarFacturasPorFecha();

        assertEquals(2, resultado.size());
        verify(ftr, times(1)).findAllByOrderByFechaEmisionAsc();
    }

    @Test
    @DisplayName("Debería retornar una factura si el ID existe")
    void buscarFacturaPorId_Existe_Test() {
        Long idBuscado = 5L;
        Factura facturaSimulada = new Factura();
        facturaSimulada.setIdOrden(100L);
        when(ftr.findById(idBuscado)).thenReturn(Optional.of(facturaSimulada));

        Factura resultado = facturaService.buscarFacturaPorId(idBuscado);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getIdOrden());
        verify(ftr, times(1)).findById(idBuscado);
    }

    @Test
    @DisplayName("Debería retornar nulo si la factura no existe")
    void buscarFacturaPorId_NoExiste_Test() {
        Long idBuscado = 99L;

        when(ftr.findById(idBuscado)).thenReturn(Optional.empty());

        Factura resultado = facturaService.buscarFacturaPorId(idBuscado);

        assertNull(resultado, "El resultado debería ser null si no se encuentra la ID");
        verify(ftr, times(1)).findById(idBuscado);
    }

}
