package com.punto_de_venta.service_proveedores.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.punto_de_venta.service_proveedores.model.Proveedor;
import com.punto_de_venta.service_proveedores.repository.ProveedorRepository;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository; 

    @InjectMocks
    private ProveedorService proveedorService; 

    @Test
    @DisplayName("Debería crear un nuevo proveedor correctamente")
    void crearProveedorTest() {
        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setNombreContacto("Juan Perez");
        
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(nuevoProveedor);

        Proveedor resultado = proveedorService.crearProveedor(nuevoProveedor);

        assertNotNull(resultado, "El proveedor creado no debería ser nulo");
        assertEquals("Juan Perez", resultado.getNombreContacto(), "El nombre del contacto debe coincidir");
        
        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    @DisplayName("Debería listar todos los proveedores")
    void listarProveedoresTest() {
           List<Proveedor> listaSimulada = List.of(new Proveedor(), new Proveedor());
        when(proveedorRepository.findAll()).thenReturn(listaSimulada);

        List<Proveedor> resultado = proveedorService.listarProveedores();

        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Debería retornar 2 proveedores");
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería retornar un proveedor al buscar por ID existente")
    void buscarProveedorId_Existe_Test() {
        Long idBuscado = 1L;
        Proveedor proveedorSimulado = new Proveedor();
        proveedorSimulado.setNombreContacto("Empresa A");
        
        when(proveedorRepository.findById(idBuscado)).thenReturn(Optional.of(proveedorSimulado));

        Proveedor resultado = proveedorService.buscarProveedorId(idBuscado);

        assertNotNull(resultado);
        assertEquals("Empresa A", resultado.getNombreContacto());
        verify(proveedorRepository, times(1)).findById(idBuscado);
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException al buscar un ID que no existe")
    void buscarProveedorId_NoExiste_Test() {
        Long idBuscado = 99L;
        when(proveedorRepository.findById(idBuscado)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            proveedorService.buscarProveedorId(idBuscado);
        });

        assertEquals("El proveedor no existe", exception.getMessage());
        verify(proveedorRepository, times(1)).findById(idBuscado);
    }

    @Test
    @DisplayName("Debería actualizar los datos de un proveedor existente")
    void actualizarProveedor_Exito_Test() {
        Long id = 1L;
        
        Proveedor proveedorExistente = new Proveedor();
        proveedorExistente.setNombreContacto("Viejo Contacto");
        proveedorExistente.setEmail("viejo@correo.com");

        Proveedor datosNuevos = new Proveedor();
        datosNuevos.setNombreContacto("Nuevo Contacto");
        datosNuevos.setEmail("nuevo@correo.com");

        when(proveedorRepository.findById(id)).thenReturn(Optional.of(proveedorExistente));
        
        when(proveedorRepository.save(any(Proveedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Proveedor resultado = proveedorService.actualizarProveedor(id, datosNuevos);

        assertNotNull(resultado);
        assertEquals("Nuevo Contacto", resultado.getNombreContacto(), "El nombre debió actualizarse");
        assertEquals("nuevo@correo.com", resultado.getEmail(), "El email debió actualizarse");
        
        verify(proveedorRepository, times(1)).findById(id);
        verify(proveedorRepository, times(1)).save(proveedorExistente); // Verifica que guardó la instancia existente mutada
    }

    @Test
    @DisplayName("Debería actualizar el estado (activo) del proveedor")
    void actualizarEstadoProveedor_Exito_Test() {
        Long id = 1L;
        
        Proveedor proveedorExistente = new Proveedor();
        proveedorExistente.setActivo(false); 

        Proveedor proveedorConNuevoEstado = new Proveedor();
        proveedorConNuevoEstado.setActivo(true); 

        when(proveedorRepository.findById(id)).thenReturn(Optional.of(proveedorExistente));
        
        String resultado = proveedorService.actualizarEstadoProveedor(id, proveedorConNuevoEstado);

        assertEquals("El estado del proveedor ha sido cambiado a: true", resultado);
        assertTrue(proveedorExistente.getActivo(), "El estado de la entidad debió cambiar a true"); 
        
        verify(proveedorRepository, times(1)).findById(id);
        verify(proveedorRepository, times(1)).save(proveedorExistente);
    }
}