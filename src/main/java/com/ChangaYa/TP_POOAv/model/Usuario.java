package com.ChangaYa.TP_POOAv.model;

import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private ObjectId id;
    private String nombreUsuario;
    private String descripcion;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String fotoPerfil;
    private String role;
    private ArrayList<Servicio> servicios;
    private ArrayList<Orden> ordenes;



}
