package com.punto_de_venta.service_catalogo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.punto_de_venta.service_catalogo.model.Categoria;
import com.punto_de_venta.service_catalogo.model.Producto;
import com.punto_de_venta.service_catalogo.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    @DisplayName("Debería crear un producto correctamente")
    void guardarProductoTest() {

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Bebestibles");

        Producto producto = new Producto();
        producto.setSku("BEB001");
        producto.setNombre("Coca Cola");
        producto.setDescripcion("Bebida lata 350ml");
        producto.setPrecioBase(1000L);
        producto.setGranel(false);
        producto.setCategoria(categoria);

        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

    Producto resultado = productoService.crearProducto(producto);

    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("BEB001", resultado.getSku());
    assertEquals("Coca Cola", resultado.getNombre());
    assertEquals("Bebida lata 350ml", resultado.getDescripcion());
    assertEquals(1000L, resultado.getPrecioBase());
    assertEquals(false, resultado.getGranel());
    assertEquals("Bebestibles", resultado.getCategoria().getNombre());

    verify(productoRepository, times(1)).save(producto);
    }

    @Test
    @DisplayName("Debería listar todos los productos correctamente")
    void listarProductosTest() {

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Bebestibles");

        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setSku("BEB001");
        producto1.setNombre("Coca Cola");
        producto1.setDescripcion("Bebida lata 350ml");
        producto1.setPrecioBase(1000L);
        producto1.setGranel(false);
        producto1.setCategoria(categoria);

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setSku("BEB002");
        producto2.setNombre("Sprite");
        producto2.setDescripcion("Bebida lata 350ml");
        producto2.setPrecioBase(900L);
        producto2.setGranel(false);
        producto2.setCategoria(categoria);

        when(productoRepository.findAll()).thenReturn(List.of(producto1, producto2));

        List<Producto> resultado = productoService.listarProductos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        assertEquals("Coca Cola", resultado.get(0).getNombre());
        assertEquals("BEB001", resultado.get(0).getSku());

        assertEquals("Sprite", resultado.get(1).getNombre());
        assertEquals("BEB002", resultado.get(1).getSku());

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería buscar un producto por ID correctamente")
    void buscarProductoIdTest() {

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Bebestibles");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setSku("BEB001");
        producto.setNombre("Coca Cola");
        producto.setDescripcion("Bebida lata 350ml");
        producto.setPrecioBase(1000L);
        producto.setGranel(false);
        producto.setCategoria(categoria);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.buscarProductoId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("BEB001", resultado.getSku());
        assertEquals("Coca Cola", resultado.getNombre());
        assertEquals("Bebestibles", resultado.getCategoria().getNombre());

        verify(productoRepository, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Debería buscar un producto por SKU correctamente")
    void buscarProductoSkuTest() {

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Bebestibles");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setSku("BEB001");
        producto.setNombre("Coca Cola");
        producto.setDescripcion("Bebida lata 350ml");
        producto.setPrecioBase(1000L);
        producto.setGranel(false);
        producto.setCategoria(categoria);

        when(productoRepository.findBySku("BEB001")).thenReturn(producto);

        Producto resultado = productoService.buscarProductoSku("BEB001");

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("BEB001", resultado.getSku());
        assertEquals("Coca Cola", resultado.getNombre());
        assertEquals("Bebestibles", resultado.getCategoria().getNombre());

        verify(productoRepository, times(1)).findBySku("BEB001");
    }

    @Test
    @DisplayName("Debería actualizar un producto correctamente")
    void actualizarProductoTest() {

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Bebestibles");

        Producto productoExistente = new Producto();
        productoExistente.setId(1L);
        productoExistente.setSku("BEB001");
        productoExistente.setNombre("Coca Cola");
        productoExistente.setDescripcion("Bebida lata 350ml");
        productoExistente.setPrecioBase(1000L);
        productoExistente.setGranel(false);
        productoExistente.setCategoria(categoria);

        Producto productoActualizado = new Producto();
        productoActualizado.setSku("BEB001-ACT");
        productoActualizado.setNombre("Coca Cola Zero");
        productoActualizado.setDescripcion("Bebida sin azúcar lata 350ml");
        productoActualizado.setPrecioBase(1200L);
        productoActualizado.setGranel(false);
        productoActualizado.setCategoria(categoria);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoExistente));

        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto p = invocation.getArgument(0);
            return p;
        });

        Producto resultado = productoService.actualizarProducto(1L, productoActualizado);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("BEB001-ACT", resultado.getSku());
        assertEquals("Coca Cola Zero", resultado.getNombre());
        assertEquals("Bebida sin azúcar lata 350ml", resultado.getDescripcion());
        assertEquals(1200L, resultado.getPrecioBase());
        assertEquals(false, resultado.getGranel());
        assertEquals("Bebestibles", resultado.getCategoria().getNombre());

        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(productoExistente);
    }
    @Test
    @DisplayName("Debería eliminar un producto correctamente")
    void eliminarProductoTest() {

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Bebestibles");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setSku("BEB001");
        producto.setNombre("Coca Cola");
        producto.setDescripcion("Bebida lata 350ml");
        producto.setPrecioBase(1000L);
        producto.setGranel(false);
        producto.setCategoria(categoria);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        String resultado = productoService.eliminarProducto(1L);

        assertNotNull(resultado);
        assertEquals("El producto ha sido eliminado exitosamente", resultado);

        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).delete(producto);
    }

    @Test
    @DisplayName("Debería retornar true si el producto existe")
    void existeProductoTrueTest() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setSku("BEB001");
        producto.setNombre("Coca Cola");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Boolean resultado = productoService.existe(1L);

        assertEquals(true, resultado);

        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar false si el producto no existe")
    void existeProductoFalseTest() {

        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Boolean resultado = productoService.existe(99L);

        assertEquals(false, resultado);

        verify(productoRepository, times(1)).findById(99L);
    }

}
