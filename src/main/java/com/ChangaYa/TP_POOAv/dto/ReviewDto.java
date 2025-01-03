package com.ChangaYa.TP_POOAv.dto;

import java.time.LocalDate;

import org.bson.types.ObjectId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ReviewDto {
    
    private ObjectId id;
    private String nombreCliente;
    private String tituloServicio;
    private String comentario; 
    private int calificacion; 
    private LocalDate fechaCalificacion;
    
}
