package com.ChangaYa.TP_POOAv.dto;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@Builder

public class CategoriaDto {
   
    private ObjectId id;
    private String nombreCategoria;
    private String descripcion;
}
