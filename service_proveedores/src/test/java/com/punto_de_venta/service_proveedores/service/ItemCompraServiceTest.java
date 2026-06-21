package com.punto_de_venta.service_proveedores.service;

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

import com.punto_de_venta.service_proveedores.model.ItemCompra;
import com.punto_de_venta.service_proveedores.model.OrdenCompra;
import com.punto_de_venta.service_proveedores.repository.ItemCompraRepository;
import com.punto_de_venta.service_proveedores.repository.OrdenCompraRepository;

@ExtendWith(MockitoExtension.class)
class ItemCompraServiceTest {

    @Mock
    private ItemCompraRepository itemCompraRepository;

    @Mock
    private OrdenCompraRepository ordenCompraRepository;

    @Mock
    private OrdenCompraService ordenCompraService;

    @InjectMocks
    private ItemCompraService itemCompraService; 

    @Test
    @DisplayName("Debería calcular el subtotal, actualizar la orden y guardar el ItemCompra")
    void crearItemCompraTest() {
        OrdenCompra ordenSimulada = new OrdenCompra();
        ordenSimulada.setId(10L);
        ordenSimulada.setCostoTotal(500L); 
        ItemCompra itemEntrante = new ItemCompra();
        itemEntrante.setCantidad(new BigDecimal("2")); 
        itemEntrante.setCostoUnitario(150L); 
        itemEntrante.setOrdenCompra(ordenSimulada);

        when(ordenCompraService.buscarOrdenCompraId(10L)).thenReturn(ordenSimulada);

        when(ordenCompraRepository.save(any(OrdenCompra.class))).thenAnswer(i -> i.getArgument(0));
        when(itemCompraRepository.save(any(ItemCompra.class))).thenAnswer(i -> i.getArgument(0));

        ItemCompra resultado = itemCompraService.crearItemCompra(itemEntrante);

        assertNotNull(resultado, "El ItemCompra no debe ser nulo");
        
        assertEquals(800L, resultado.getOrdenCompra().getCostoTotal(), "El costo total de la orden no se sumó correctamente");

        verify(ordenCompraService, times(1)).buscarOrdenCompraId(10L);
        verify(ordenCompraRepository, times(1)).save(any(OrdenCompra.class));
        verify(itemCompraRepository, times(1)).save(itemEntrante);
    }

    @Test
    @DisplayName("Debería listar todos los ítems de compra")
    void listarItemsCompraTest() {
        List<ItemCompra> listaSimulada = List.of(new ItemCompra(), new ItemCompra(), new ItemCompra());
        when(itemCompraRepository.findAll()).thenReturn(listaSimulada);

        List<ItemCompra> resultado = itemCompraService.listarItemsCompra();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        verify(itemCompraRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería retornar un ítem al buscar por ID existente")
    void buscarItemCompraId_Existe_Test() {
        Long idBuscado = 5L;
        ItemCompra itemSimulado = new ItemCompra();
        itemSimulado.setCostoUnitario(999L);
        
        when(itemCompraRepository.findById(idBuscado)).thenReturn(Optional.of(itemSimulado));

        ItemCompra resultado = itemCompraService.buscarItemCompraId(idBuscado);

        assertNotNull(resultado);
        assertEquals(999L, resultado.getCostoUnitario());
        verify(itemCompraRepository, times(1)).findById(idBuscado);
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException al buscar un ID que no existe")
    void buscarItemCompraId_NoExiste_Test() {
        Long idBuscado = 99L;
        when(itemCompraRepository.findById(idBuscado)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            itemCompraService.buscarItemCompraId(idBuscado);
        });

        assertEquals("El Item de compra que desea buscar no existe", exception.getMessage());
        verify(itemCompraRepository, times(1)).findById(idBuscado);
    }
}