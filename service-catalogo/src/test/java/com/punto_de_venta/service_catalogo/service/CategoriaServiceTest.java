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
import com.punto_de_venta.service_catalogo.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock 
    private CategoriaRepository categoriaRepository;
    @InjectMocks
    private CategoriaService categoriaService;

    @Test 
    @DisplayName("Debería guardar una categoría correctamente")
    
    void guardarCategoriaTest(){

        Categoria categoria = new Categoria();
        categoria.setNombre("Bebestibles");

        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> {
            Categoria c = invocation.getArgument(0);
            c.setId(1L); 
            return c;
        });

        Categoria resultado = categoriaService.crearCategoria(categoria);
        assertNotNull(resultado);
        assertEquals(1L,resultado.getId());
        assertEquals("Bebestibles",resultado.getNombre());
        verify(categoriaRepository, times(1)).save(categoria);

    }

    @Test
    @DisplayName("Debería listar todas las categorías correctamente")
    void listarCategoriasTest() {

        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNombre("Bebestibles");

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNombre("Snacks");

        when(categoriaRepository.findAll()).thenReturn(List.of(categoria1, categoria2));

        List<Categoria> resultado = categoriaService.listarCategorias();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Bebestibles", resultado.get(0).getNombre());
        assertEquals("Snacks", resultado.get(1).getNombre());

        verify(categoriaRepository, times(1)).findAll();
    }   

    @Test
    @DisplayName("Debería buscar la categoría por Id correctamente")
    void buscarPorIdTest(){
        
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Bebestibles");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Categoria resultado = categoriaService.buscarCategoriaId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Bebestibles", resultado.getNombre());
        verify(categoriaRepository, times(1)).findById(1L);
    }


    @Test
    @DisplayName("Debería actualizar una categoría correctamente")
    void actualizarCategoriaTest(){

        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(1L);
        categoriaExistente.setNombre("Bebestibles");

        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setNombre("Lácteos");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaExistente));

         when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation ->{
            Categoria c = invocation.getArgument(0);
            return c;
        });

        Categoria resultado = categoriaService.actualizarCategoria(1L, categoriaActualizada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Lácteos", resultado.getNombre());

        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).save(categoriaExistente);
    }

    @Test
    @DisplayName("Debería eliminar una categoria correctamente")
    void eliminarCategoriaTest(){

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Bebestibles");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        String resultado = categoriaService.eliminarCategoria(1L);

        assertNotNull(resultado);
        assertEquals("La categoria ha sido eliminada exitosamente", resultado);

        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).delete(categoria);
    }

}
