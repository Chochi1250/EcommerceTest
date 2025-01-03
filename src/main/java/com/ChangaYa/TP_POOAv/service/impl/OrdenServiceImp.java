package com.ChangaYa.TP_POOAv.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ChangaYa.TP_POOAv.dto.OrdenDto;
import com.ChangaYa.TP_POOAv.model.Orden;
import com.ChangaYa.TP_POOAv.repository.OrdenRepository;
import com.ChangaYa.TP_POOAv.service.OrdenService;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImp implements OrdenService {
    private OrdenRepository ordenRepository;

    public OrdenServiceImp(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    @Override
    public List<OrdenDto> findAllOrdenes() {
        List<Orden> ordenes = ordenRepository.findAll();
        return ordenes.stream().map(this::mapToOrdenDto).collect(Collectors.toList());
    }

    @Override
    public Orden saveOrden(OrdenDto ordenDto) {
      
        Orden orden = mapToOrden(ordenDto);
        
        return ordenRepository.save(orden);
    }

    private OrdenDto mapToOrdenDto(Orden orden) {
        return OrdenDto.builder()
            .id(orden.getId())
            .usuarioCliente(orden.getUsuarioCliente())
            .tituloServicio(orden.getTituloServicio())
            .precioServicio(orden.getPrecioServicio())
            .fechaOrden(orden.getFechaOrden())
            .estadoOrden(orden.getEstadoOrden())
            .fechaEntrega(orden.getFechaEntrega())
            .usuarioFreelancer(orden.getUsuarioFreelancer())
            .build();
    }

    private Orden mapToOrden(OrdenDto ordenDto) {
        Orden orden = new Orden();
        orden.setId(ordenDto.getId());
        orden.setUsuarioCliente(ordenDto.getUsuarioCliente());
        orden.setTituloServicio(ordenDto.getTituloServicio());
        orden.setPrecioServicio(ordenDto.getPrecioServicio());
        orden.setFechaOrden(ordenDto.getFechaOrden());
        orden.setEstadoOrden(ordenDto.getEstadoOrden());
        orden.setFechaEntrega(ordenDto.getFechaEntrega());
        orden.setUsuarioFreelancer(ordenDto.getUsuarioFreelancer());
        return orden;
    }
    
    @Override
    public OrdenDto findOrdenById(ObjectId id) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        return mapToOrdenDto(orden);
    }
    
    @Override
    public List<Orden> findOrdenesByUsuario(String nombreUsuario) {
        List<Orden> ordenesComoCliente = ordenRepository.findByUsuarioCliente(nombreUsuario);
        List<Orden> ordenesComoFreelancer = ordenRepository.findByUsuarioFreelancer(nombreUsuario);

        // Combina ambas listas en una sola
        List<Orden> ordenes = new ArrayList<>();
        ordenes.addAll(ordenesComoCliente);
        ordenes.addAll(ordenesComoFreelancer);

        return ordenes;
    }
}
