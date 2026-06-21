package com.punto_de_venta.service_proveedores.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.punto_de_venta.service_proveedores.model.ItemCompra;
import com.punto_de_venta.service_proveedores.model.OrdenCompra;
import com.punto_de_venta.service_proveedores.model.Proveedor;
import com.punto_de_venta.service_proveedores.repository.OrdenCompraRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class OrdenCompraServiceTest {

    @Mock
    private OrdenCompraRepository ordenCompraRepository;

    @Mock
    private ProveedorService proveedorService;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private OrdenCompraService ordenCompraService;

    @Test
    @DisplayName("Debería crear una orden de compra iniciando su costo en 0")
    void crearOrdenTest() {
        Proveedor proveedorSimulado = new Proveedor();
        proveedorSimulado.setId(1L);

        OrdenCompra nuevaOrden = new OrdenCompra();
        nuevaOrden.setProveedor(proveedorSimulado);

        when(proveedorService.buscarProveedorId(1L)).thenReturn(proveedorSimulado);
        
        when(ordenCompraRepository.save(any(OrdenCompra.class))).thenAnswer(i -> i.getArgument(0));

        OrdenCompra resultado = ordenCompraService.crearOrden(nuevaOrden);

        assertNotNull(resultado);
        assertEquals(0L, resultado.getCostoTotal(), "El costo inicial debe setearse en 0L");
        
        verify(proveedorService, times(1)).buscarProveedorId(1L);
        verify(ordenCompraRepository, times(1)).save(any(OrdenCompra.class));
    }

    @Test
    @DisplayName("Debería listar las órdenes e intentar cargar los datos de los productos mediante WebClient")
    void listarOrdenesTest() {
        ItemCompra item = new ItemCompra();
        item.setIdProducto(5L);
        
        List<ItemCompra> items = new ArrayList<>();
        items.add(item);

        OrdenCompra orden = new OrdenCompra();
        orden.setItems(items);

        List<OrdenCompra> listaOrdenes = List.of(orden);

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(ordenCompraRepository.findAll()).thenReturn(listaOrdenes);
        
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        
        Object productoSimulado = new Object(); 
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(productoSimulado));

        List<OrdenCompra> resultado = ordenCompraService.listarOrdenes();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertNotNull(resultado.get(0).getItems().get(0).getDatosProducto(), "Debería haber inyectado los datos del producto");
        
        verify(ordenCompraRepository, times(1)).findAll();
        verify(webClientBuilder, times(1)).build();
    }

    @Test
    @DisplayName("Debería actualizar el estado de la orden y gatillar un movimiento de inventario si pasa a RECIBIDA")
    void actualizarStatusOrdenCompra_Recibida_Test() {
        
        Long idOrden = 1L;
        
        OrdenCompra ordenExistente = new OrdenCompra();
        ordenExistente.setStatus("PENDIENTE"); 
        
        ItemCompra item = new ItemCompra();
        item.setIdProducto(10L);
        item.setCantidad(new java.math.BigDecimal("50"));
        ordenExistente.setItems(List.of(item));

        OrdenCompra ordenNuevosDatos = new OrdenCompra();
        ordenNuevosDatos.setStatus("RECIBIDA"); 

        when(ordenCompraRepository.findById(idOrden)).thenReturn(Optional.of(ordenExistente));
        when(ordenCompraRepository.save(any(OrdenCompra.class))).thenAnswer(i -> i.getArgument(0));

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestBodyUriSpec postUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec postBodySpec = Mockito.mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec postHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec postResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);
        
        WebClient.RequestHeadersUriSpec getUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec getHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec getResponseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        
        when(webClient.post()).thenReturn(postUriSpec);
        when(postUriSpec.uri(anyString())).thenReturn(postBodySpec);
        when(postBodySpec.bodyValue(any())).thenReturn(postHeadersSpec);
        when(postHeadersSpec.retrieve()).thenReturn(postResponseSpec);
        when(postResponseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        when(webClient.get()).thenReturn(getUriSpec);
        when(getUriSpec.uri(anyString())).thenReturn(getHeadersSpec);
        when(getHeadersSpec.retrieve()).thenReturn(getResponseSpec);
        when(getResponseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(new Object()));

        OrdenCompra resultado = ordenCompraService.actualizarStatusOrdenCompra(idOrden, ordenNuevosDatos);

        assertEquals("RECIBIDA", resultado.getStatus(), "El estado debió actualizarse a RECIBIDA");
        
        verify(webClient, times(1)).post();
        verify(webClient, times(1)).get();
    }
}