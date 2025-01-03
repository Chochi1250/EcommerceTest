package com.ChangaYa.TP_POOAv.dto;

import java.time.LocalDate;

import javax.persistence.Entity;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ChangaYa.TP_POOAv.model.EstadoOrden;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrdenDto {
   
    private ObjectId id;
    private String tituloServicio;
    private String usuarioCliente;
    private double precioServicio;
    private LocalDate fechaOrden;
    private EstadoOrden estadoOrden;
    private LocalDate fechaEntrega;
    private String usuarioFreelancer;

}
