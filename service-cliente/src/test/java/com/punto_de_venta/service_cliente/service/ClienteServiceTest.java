package com.punto_de_venta.service_cliente.service;

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

import com.punto_de_venta.service_cliente.model.Cliente;
import com.punto_de_venta.service_cliente.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Debería crear un cliente correctamente")

    void guardarClienteTest(){

        Cliente cliente = new Cliente();
        cliente.setRun(12345678L);
        cliente.setDvrun('5');
        cliente.setPNombre("Catalina");
        cliente.setSNombre("Martina");
        cliente.setApPaterno("Gonzales");
        cliente.setApMaterno("Herrera");
        cliente.setCorreo("catalina@gmail.com");
        cliente.setTelefono(984848484L);
        cliente.setActivo(true);

        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        Cliente resultado = clienteService.crearCliente(cliente);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(12345678L, resultado.getRun());
        assertEquals('5', resultado.getDvrun());
        assertEquals("Catalina", resultado.getPNombre());
        assertEquals("Martina", resultado.getSNombre());
        assertEquals("Gonzales", resultado.getApPaterno());
        assertEquals("Herrera", resultado.getApMaterno());
        assertEquals("catalina@gmail.com", resultado.getCorreo());
        assertEquals(984848484L, resultado.getTelefono());
        assertEquals(true, resultado.getActivo());
        verify(clienteRepository, times(1)).save(cliente); 
    }

    @Test
    @DisplayName("Debería listar los clientes correctamente")
    void listarClientesTest(){

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setRun(12345678L);
        cliente.setDvrun('5');
        cliente.setPNombre("Catalina");
        cliente.setSNombre("Martina");
        cliente.setApPaterno("Gonzales");
        cliente.setApMaterno("Herrera");
        cliente.setCorreo("catalina@gmail.com");
        cliente.setTelefono(984848484L);
        cliente.setActivo(true);

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setRun(21345678L);
        cliente2.setDvrun('7');
        cliente2.setPNombre("Camila");
        cliente2.setSNombre("Alejandra");
        cliente2.setApPaterno("Parra");
        cliente2.setApMaterno("Rojas");
        cliente2.setCorreo("camila@gmail.com");
        cliente2.setTelefono(91234546789L);
        cliente2.setActivo(true);

        when(clienteRepository.findAll()).thenReturn(List.of(cliente,cliente2));

        List<Cliente> resultado = clienteService.listarClientes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals(12345678L, resultado.get(0).getRun());
        assertEquals('5', resultado.get(0).getDvrun());
        assertEquals("Catalina", resultado.get(0).getPNombre());
        assertEquals("Martina", resultado.get(0).getSNombre());
        assertEquals("Gonzales", resultado.get(0).getApPaterno());
        assertEquals("Herrera", resultado.get(0).getApMaterno());
        assertEquals("catalina@gmail.com", resultado.get(0).getCorreo());
        assertEquals(984848484L, resultado.get(0).getTelefono());
        assertEquals(true, resultado.get(0).getActivo());

        assertEquals(2L, resultado.get(1).getId());
        assertEquals(21345678L, resultado.get(1).getRun());
        assertEquals('7', resultado.get(1).getDvrun());
        assertEquals("Camila", resultado.get(1).getPNombre());
        assertEquals("Alejandra", resultado.get(1).getSNombre());
        assertEquals("Parra", resultado.get(1).getApPaterno());
        assertEquals("Rojas", resultado.get(1).getApMaterno());
        assertEquals("camila@gmail.com", resultado.get(1).getCorreo());
        assertEquals(91234546789L, resultado.get(1).getTelefono());
        assertEquals(true, resultado.get(1).getActivo());

        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería buscar al cliente por Id correctamente")
    void buscarClienteId(){

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setRun(12345678L);
        cliente.setDvrun('5');
        cliente.setPNombre("Catalina");
        cliente.setSNombre("Martina");
        cliente.setApPaterno("Gonzales");
        cliente.setApMaterno("Herrera");
        cliente.setCorreo("catalina@gmail.com");
        cliente.setTelefono(984848484L);
        cliente.setActivo(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.buscarClienteId(1L);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(12345678L, resultado.getRun());
        assertEquals('5', resultado.getDvrun());
        assertEquals("Catalina", resultado.getPNombre());
        assertEquals("Martina", resultado.getSNombre());
        assertEquals("Gonzales", resultado.getApPaterno());
        assertEquals("Herrera", resultado.getApMaterno());
        assertEquals("catalina@gmail.com", resultado.getCorreo());
        assertEquals(984848484L, resultado.getTelefono());
        assertEquals(true, resultado.getActivo());
        verify(clienteRepository, times(1)).findById(1L); 
    }

    @Test
    @DisplayName("Debería buscar a un cliente por run correctamente")
    void buscarClienteRunTest(){

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setRun(12345678L);
        cliente.setDvrun('5');
        cliente.setPNombre("Catalina");
        cliente.setSNombre("Martina");
        cliente.setApPaterno("Gonzales");
        cliente.setApMaterno("Herrera");
        cliente.setCorreo("catalina@gmail.com");
        cliente.setTelefono(984848484L);
        cliente.setActivo(true);

        when(clienteRepository.findByRun(12345678L)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.buscarClienteRun(12345678L);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(12345678L, resultado.getRun());
        assertEquals('5', resultado.getDvrun());
        assertEquals("Catalina", resultado.getPNombre());
        assertEquals("Martina", resultado.getSNombre());
        assertEquals("Gonzales", resultado.getApPaterno());
        assertEquals("Herrera", resultado.getApMaterno());
        assertEquals("catalina@gmail.com", resultado.getCorreo());
        assertEquals(984848484L, resultado.getTelefono());
        assertEquals(true, resultado.getActivo());
        verify(clienteRepository, times(1)).findByRun(12345678L);
    }


    @Test
    @DisplayName("Debería actualizar a un cliente correctamente")

    void actualizarClienteTest(){

        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(1L);
        clienteExistente.setRun(12345678L);
        clienteExistente.setDvrun('5');
        clienteExistente.setPNombre("Catalina");
        clienteExistente.setSNombre("Martina");
        clienteExistente.setApPaterno("Gonzales");
        clienteExistente.setApMaterno("Herrera");
        clienteExistente.setCorreo("catalina@gmail.com");
        clienteExistente.setTelefono(984848484L);
        clienteExistente.setActivo(true);

        Cliente clienteActualizar = new Cliente();
        clienteActualizar.setId(2L);
        clienteActualizar.setRun(21345678L);
        clienteActualizar.setDvrun('7');
        clienteActualizar.setPNombre("Camila");
        clienteActualizar.setSNombre("Alejandra");
        clienteActualizar.setApPaterno("Parra");
        clienteActualizar.setApMaterno("Rojas");
        clienteActualizar.setCorreo("camila@gmail.com");
        clienteActualizar.setTelefono(91234546789L);
        clienteActualizar.setActivo(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

        String resultado = clienteService.actualizarEstadoCliente(1L, false);

        assertNotNull(resultado);
        assertEquals("El estado del cliente ha sido actualizado", resultado);
        assertEquals(false, clienteExistente.getActivo());

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(clienteExistente);    
    }

    @Test
    @DisplayName("Debería actualizar el estado del cliente correctamente")
    void actualizarEstadoClienteTest() {

        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(1L);
        clienteExistente.setRun(12345678L);
        clienteExistente.setDvrun('9');
        clienteExistente.setPNombre("Carolina");
        clienteExistente.setSNombre("Andrea");
        clienteExistente.setApPaterno("Hermosilla");
        clienteExistente.setApMaterno("Baeza");
        clienteExistente.setCorreo("cliente@correo.cl");
        clienteExistente.setTelefono(987654321L);
        clienteExistente.setActivo(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            return c;
        });

        String resultado = clienteService.actualizarEstadoCliente(1L, false);

        assertNotNull(resultado);
        assertEquals("El estado del cliente ha sido actualizado", resultado);
        assertEquals(false, clienteExistente.getActivo());

        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(clienteExistente);
    }
}
