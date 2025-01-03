package com.ChangaYa.TP_POOAv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Document(collection = "servicios")

public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;
    private String titulo;
    private String descripcion;
    private double precio;
    private  EstadoServicio estadoServicio;
    private String idFreeLancer;
    private ArrayList<String> categorias;
    private ArrayList<String> reviews;
    @CreationTimestamp
    private LocalDate fechaCreacion;
    private String photoUrl;
    private int duracionDias;

}

