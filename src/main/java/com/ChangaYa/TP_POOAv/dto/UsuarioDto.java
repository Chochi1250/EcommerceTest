package com.ChangaYa.TP_POOAv.dto;
import java.util.List;

import org.bson.types.ObjectId;
import com.ChangaYa.TP_POOAv.model.Servicio;
import com.ChangaYa.TP_POOAv.model.Orden;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class UsuarioDto {
   
    private ObjectId id;
    private String nombreUsuario;
    private String contrasena;
    private String descripcion;
    private String role;
    private List<Servicio> servicios;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String fotoPerfil;
    private List<Orden> ordenes;

}
