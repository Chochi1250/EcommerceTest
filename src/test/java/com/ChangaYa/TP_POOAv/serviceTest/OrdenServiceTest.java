package com.ChangaYa.TP_POOAv.serviceTest;

import com.ChangaYa.TP_POOAv.dto.OrdenDto;
import com.ChangaYa.TP_POOAv.model.Orden;
import com.ChangaYa.TP_POOAv.repository.OrdenRepository;
import com.ChangaYa.TP_POOAv.service.impl.OrdenServiceImp;
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

class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @InjectMocks
    private OrdenServiceImp ordenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllOrdenes() {
        Orden orden1 = new Orden();
        orden1.setTituloServicio("Servicio 1");
        Orden orden2 = new Orden();
        orden2.setTituloServicio("Servicio 2");

        when(ordenRepository.findAll()).thenReturn(Arrays.asList(orden1, orden2));

        List<OrdenDto> result = ordenService.findAllOrdenes();

        assertEquals(2, result.size());
        assertEquals("Servicio 1", result.get(0).getTituloServicio());
        assertEquals("Servicio 2", result.get(1).getTituloServicio());
        verify(ordenRepository, times(1)).findAll();
    }

    @Test
    void testSaveOrden() {
        OrdenDto ordenDto = OrdenDto.builder()
                .id(new ObjectId())
                .tituloServicio("Nuevo Servicio")
                .precioServicio(150.00)
                .fechaOrden(LocalDate.now())
                .build();

        Orden orden = new Orden();
        orden.setTituloServicio("Nuevo Servicio");

        when(ordenRepository.save(any(Orden.class))).thenReturn(orden);

        Orden result = ordenService.saveOrden(ordenDto);

        assertEquals("Nuevo Servicio", result.getTituloServicio());
        verify(ordenRepository, times(1)).save(any(Orden.class));
    }

    @Test
    void testFindOrdenById() {
        ObjectId id = new ObjectId();
        Orden orden = new Orden();
        orden.setTituloServicio("Orden encontrada");

        when(ordenRepository.findById(id)).thenReturn(Optional.of(orden));

        OrdenDto result = ordenService.findOrdenById(id);

        assertEquals("Orden encontrada", result.getTituloServicio());
        verify(ordenRepository, times(1)).findById(id);
    }

    @Test
    void testFindOrdenesByUsuario() {
        String nombreUsuario = "clienteTest";
        
        Orden ordenComoCliente = new Orden();
        ordenComoCliente.setTituloServicio("Orden como Cliente");
        ordenComoCliente.setUsuarioCliente(nombreUsuario);
        
        Orden ordenComoFreelancer = new Orden();
        ordenComoFreelancer.setTituloServicio("Orden como Freelancer");
        ordenComoFreelancer.setUsuarioFreelancer(nombreUsuario);

        when(ordenRepository.findByUsuarioCliente(nombreUsuario)).thenReturn(Arrays.asList(ordenComoCliente));
        when(ordenRepository.findByUsuarioFreelancer(nombreUsuario)).thenReturn(Arrays.asList(ordenComoFreelancer));

        List<Orden> result = ordenService.findOrdenesByUsuario(nombreUsuario);

        assertEquals(2, result.size());
        assertTrue(result.contains(ordenComoCliente));
        assertTrue(result.contains(ordenComoFreelancer));
        verify(ordenRepository, times(1)).findByUsuarioCliente(nombreUsuario);
        verify(ordenRepository, times(1)).findByUsuarioFreelancer(nombreUsuario);
    }
}

