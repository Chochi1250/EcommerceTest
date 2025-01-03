package com.ChangaYa.TP_POOAv.service;

import java.util.List;
import com.ChangaYa.TP_POOAv.dto.AuthRequestDto;
import com.ChangaYa.TP_POOAv.dto.UsuarioDto;
import com.ChangaYa.TP_POOAv.model.Servicio;
import com.ChangaYa.TP_POOAv.model.Usuario;

public interface UsuarioService {
    List<UsuarioDto> findAllUsuarios();
    Usuario findByUsername(String username);
    UsuarioDto findByNombre(String nombre);
    void saveUsuario(AuthRequestDto user);
    void updateUsuario(UsuarioDto usuarioDto);
    void addServiceToUser(String nombreUsuario, Servicio nuevoServicio);
    UsuarioDto findUsuarioByNombre(String nombreUsuario);
    List<Servicio> getServiciosAsociados(String nombreUsuario);
}
