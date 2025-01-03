package com.ChangaYa.TP_POOAv.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import org.bson.types.ObjectId;

import lombok.Builder;
import lombok.Data;


import com.ChangaYa.TP_POOAv.model.EstadoServicio;




@Data
@Builder


public class ServicioDto {

    private ObjectId id;
    private String titulo;
    
    private Double precio;
   
    private String descripcion;
    private  EstadoServicio estadoServicio;
    private String idFreeLancer;
    private LocalDate fechaCreacion;
    
    private ArrayList<String> categorias;
    private ArrayList<String> reviews;
   
    private String photoUrl;
    private int duracionDias;
}
