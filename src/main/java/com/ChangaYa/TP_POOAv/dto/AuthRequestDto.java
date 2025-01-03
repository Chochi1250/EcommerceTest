package com.ChangaYa.TP_POOAv.dto;
import lombok.Data;



@Data

public class AuthRequestDto {

    private String nombreUsuario;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String fotoPerfil;
    private String descripcion;
    
}
