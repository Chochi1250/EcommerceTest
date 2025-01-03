package com.ChangaYa.TP_POOAv.service;

import java.util.List;
import com.ChangaYa.TP_POOAv.model.Servicio;

import com.ChangaYa.TP_POOAv.dto.ServicioDto;
import org.bson.types.ObjectId;

public interface ServicioService {
    List<ServicioDto> findAllServicios();
    Servicio saveServicio(ServicioDto nuevoServicio);
    ServicioDto findServicioById(ObjectId id);
    ServicioDto findServicioByTitulo(String titulo);
    void updateServicio(ServicioDto servicioDto);
    void deleteServicio(ObjectId id);
    List<ServicioDto> searchServicios(String query);

}
