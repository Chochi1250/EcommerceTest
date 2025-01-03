package com.ChangaYa.TP_POOAv.serviceTest;

import com.ChangaYa.TP_POOAv.dto.ServicioDto;
import com.ChangaYa.TP_POOAv.model.Servicio;
import com.ChangaYa.TP_POOAv.repository.ServicioRepository;
import com.ChangaYa.TP_POOAv.service.impl.ServicioServiceImp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicioServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioServiceImp servicioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchServicios() {
        String query = "test";
        Servicio servicio = new Servicio();
        servicio.setTitulo("test");

        when(servicioRepository.searchServicios(query)).thenReturn(Arrays.asList(servicio));

        List<ServicioDto> result = servicioService.searchServicios(query);
        
        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getTitulo());
        verify(servicioRepository, times(1)).searchServicios(query);
    }

    @Test
    void testUpdateServicio() {
        ServicioDto servicioDto = ServicioDto.builder()
                .id(new ObjectId())
                .titulo("Updated Title")
                .precio(100.00)
                .build();

        when(servicioRepository.save(any(Servicio.class))).thenReturn(new Servicio());

        servicioService.updateServicio(servicioDto);

        verify(servicioRepository, times(1)).save(any(Servicio.class));
    }

    @Test
    void testFindAllServicios() {
        Servicio servicio1 = new Servicio();
        servicio1.setTitulo("Servicio 1");
        Servicio servicio2 = new Servicio();
        servicio2.setTitulo("Servicio 2");

        when(servicioRepository.findAll()).thenReturn(Arrays.asList(servicio1, servicio2));

        List<ServicioDto> result = servicioService.findAllServicios();

        assertEquals(2, result.size());
        verify(servicioRepository, times(1)).findAll();
    }

    @Test
    void testSaveServicio() {
        ServicioDto servicioDto = ServicioDto.builder()
                .titulo("New Servicio")
                .descripcion("Descripción")
                .precio(100.00)
                .fechaCreacion(LocalDate.now())
                .build();
                
        Servicio servicio = new Servicio();
        servicio.setTitulo("New Servicio");

        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicio);

        Servicio result = servicioService.saveServicio(servicioDto);

        assertEquals("New Servicio", result.getTitulo());
        verify(servicioRepository, times(1)).save(any(Servicio.class));
    }

    @Test
    void testDeleteServicio() {
        ObjectId id = new ObjectId();
        
        doNothing().when(servicioRepository).deleteById(id);

        servicioService.deleteServicio(id);

        verify(servicioRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindServicioById() {
        ObjectId id = new ObjectId();
        Servicio servicio = new Servicio();
        servicio.setTitulo("Servicio encontrado");

        when(servicioRepository.findById(id)).thenReturn(Optional.of(servicio));

        ServicioDto result = servicioService.findServicioById(id);

        assertEquals("Servicio encontrado", result.getTitulo());
        verify(servicioRepository, times(1)).findById(id);
    }

    @Test
    void testFindServicioByTitulo() {
        String titulo = "Servicio prueba";
        Servicio servicio = new Servicio();
        servicio.setTitulo(titulo);

        when(servicioRepository.findByTitulo(titulo)).thenReturn(Optional.of(servicio));

        ServicioDto result = servicioService.findServicioByTitulo(titulo);

        assertEquals(titulo, result.getTitulo());
        verify(servicioRepository, times(1)).findByTitulo(titulo);
    }
}
