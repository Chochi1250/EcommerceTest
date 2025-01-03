package com.ChangaYa.TP_POOAv.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.ChangaYa.TP_POOAv.dto.OrdenDto;
import com.ChangaYa.TP_POOAv.model.Orden;

public interface OrdenService {
    List<OrdenDto> findAllOrdenes();
    Orden saveOrden(OrdenDto ordenDto);
    OrdenDto findOrdenById(ObjectId id);
    List<Orden> findOrdenesByUsuario(String nombreUsuario);
    
}
