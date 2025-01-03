package com.ChangaYa.TP_POOAv.service.impl;
import com.ChangaYa.TP_POOAv.dto.ServicioDto;
import com.ChangaYa.TP_POOAv.repository.ServicioRepository;
import com.ChangaYa.TP_POOAv.model.Servicio;
import com.ChangaYa.TP_POOAv.service.ServicioService;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;


@Service
public class ServicioServiceImp implements ServicioService {

    

    private ServicioRepository servicioRepository;

    
    public ServicioServiceImp(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<ServicioDto> searchServicios(String query){
        List<Servicio> servicios = servicioRepository.searchServicios(query);
        return servicios.stream().map((servicio) -> mapToServicioDto(servicio)).collect(Collectors.toList());
    }

    @Override
    public void updateServicio(ServicioDto servicioDto) {
        
        Servicio servicio = mapToServicio(servicioDto);

       
        servicio.setId(servicioDto.getId());

        servicioRepository.save(servicio);
    }

    private Servicio mapToServicio(ServicioDto servicioDto){
        Servicio servicio = Servicio.builder()
        .id(servicioDto.getId())
        .titulo(servicioDto.getTitulo())
        .descripcion(servicioDto.getDescripcion())
        .precio(servicioDto.getPrecio())
        .estadoServicio(servicioDto.getEstadoServicio())
        .fechaCreacion(servicioDto.getFechaCreacion())
        .idFreeLancer(servicioDto.getIdFreeLancer())
        .categorias(servicioDto.getCategorias())
        .reviews(servicioDto.getReviews())
        .photoUrl(servicioDto.getPhotoUrl())
        .duracionDias(servicioDto.getDuracionDias())
        .build();
        return servicio;
    }

	@Override
	public List<ServicioDto> findAllServicios() {
		List<Servicio> servicios = servicioRepository.findAll();  
        return servicios.stream().map((servicio) -> mapToServicioDto(servicio)).collect(Collectors.toList());
	}

    public Servicio saveServicio(ServicioDto servicioDto){
        Servicio servicio = mapToServicio(servicioDto);
        return servicioRepository.save(servicio);
    }
    public void deleteServicio(ObjectId id){
        servicioRepository.deleteById(id);
    }

    @Override
    public ServicioDto findServicioById(ObjectId id) {
        Servicio servicio = servicioRepository.findById(id).orElseThrow(() -> new RuntimeException("Servicio not found"));
        return mapToServicioDto(servicio);
    }

    public ServicioDto findServicioByTitulo(String titulo){
        Servicio servicio = servicioRepository.findByTitulo(titulo).orElseThrow(() -> new RuntimeException("Servicio not found"));
        return mapToServicioDto(servicio);
    }
  
    private ServicioDto mapToServicioDto(Servicio servicio){
        ServicioDto servicioDto = ServicioDto.builder()
        .id(servicio.getId())
        .titulo(servicio.getTitulo())
        .descripcion(servicio.getDescripcion())
        .precio(servicio.getPrecio())
        .estadoServicio(servicio.getEstadoServicio())
        .fechaCreacion(servicio.getFechaCreacion())
        .idFreeLancer(servicio.getIdFreeLancer())
        .categorias(servicio.getCategorias())
        .reviews(servicio.getReviews())
        .photoUrl(servicio.getPhotoUrl())
        .duracionDias(servicio.getDuracionDias())
        .build();
        return servicioDto;
    }

   
}
