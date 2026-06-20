package com.punto_de_venta.service_reportes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.punto_de_venta.service_reportes.model.HistorialReporte;
import com.punto_de_venta.service_reportes.repository.HistorialReporteRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {

    @Mock
    private HistorialReporteRepository repositorio;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private ReporteService service;

    @Test
    @DisplayName("Debería obtener el historial de reporte correctamente")
    void obtenerHistorialTest(){
        HistorialReporte reporte1 = new HistorialReporte();
        HistorialReporte reporte2 = new HistorialReporte();
        List<HistorialReporte> listaSimulada = Arrays.asList(reporte1, reporte2);

        when(repositorio.findAll()).thenReturn(listaSimulada);

        List<HistorialReporte> resultado = service.obtenerHistorial();

        assertNotNull(resultado, "La lista no debe ser nula");
        assertEquals(2, resultado.size(), "Debe retornar los dos elementos simulados");
        verify(repositorio, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería generar un reporte consolidado consultando a otros microservicios")
    @SuppressWarnings("unchecked")
    void generarReporteConsolidadoTest(){
        String tipo = "MENSUAL";
        String usuario = "SYSTEM";
        Long valorSimuladoMicroservicios=1500L;

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(Long.class)).thenReturn(Mono.just(valorSimuladoMicroservicios));

        when(repositorio.save(any(HistorialReporte.class))).thenAnswer(invocation -> {
            HistorialReporte r = invocation.getArgument(0);
            return r;
        });

        HistorialReporte resultado = service.generarReporteConsolidado(tipo, usuario);

        assertNotNull(resultado, "el reporte generado no puede ser nulo");
        assertEquals(tipo, resultado.getTipoReporte(), "El tipo de reporte debe coincidir");
        assertEquals(usuario, resultado.getGeneradoPor(), "El usuario debe coincidir");

        assertEquals(1500L, resultado.getTotalVentasCalculadas());
        assertEquals(1500L, resultado.getAlertasStockDetectadas());
        assertNotNull(resultado.getFechaGeneracion(), "Debe tener una fecha asignada");

        verify(repositorio, times(1)).save(any(HistorialReporte.class));
    }
    

        
}
