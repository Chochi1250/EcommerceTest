package com.ChangaYa.TP_POOAv.service.impl;

import com.ChangaYa.TP_POOAv.dto.AuthRequestDto;
import com.ChangaYa.TP_POOAv.dto.UsuarioDto;
import com.ChangaYa.TP_POOAv.repository.UsuarioRepository;
import com.ChangaYa.TP_POOAv.model.Servicio;
import com.ChangaYa.TP_POOAv.model.Usuario;
import com.ChangaYa.TP_POOAv.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UsuarioServiceImp(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

	@Override
	public List<UsuarioDto> findAllUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();  
        return usuarios.stream().map((usuario) -> mapToUsuarioDto(usuario)).collect(Collectors.toList());
	}

    @Override
    public void updateUsuario(UsuarioDto usuarioDto) {
        Usuario usuario = mapToUsuario(usuarioDto);
        usuarioRepository.save(usuario);
    }
    
    @Override
    public UsuarioDto findByNombre(String nombre){

        Usuario usuario = usuarioRepository.findBynombre(nombre).orElseThrow(() -> new RuntimeException("Usuario no encontrado con nombre: " + nombre));

        return mapToUsuarioDto(usuario);

    }
    public Usuario mapToUsuario(UsuarioDto usuarioDto) {
        return Usuario.builder()
            .id(usuarioDto.getId())
            .nombreUsuario(usuarioDto.getNombreUsuario())
            .contrasena(usuarioDto.getContrasena())
            .descripcion(usuarioDto.getDescripcion())
            .nombre(usuarioDto.getNombre())
            .apellido(usuarioDto.getApellido())
            .email(usuarioDto.getEmail())
            .fotoPerfil(usuarioDto.getFotoPerfil())
            .telefono(usuarioDto.getTelefono())
            .servicios(usuarioDto.getServicios() != null ? new ArrayList<>(usuarioDto.getServicios()) : new ArrayList<>()) // Inicializa con lista vacía si es null
            .ordenes(usuarioDto.getOrdenes() != null ? new ArrayList<>(usuarioDto.getOrdenes()) : new ArrayList<>()) // Inicializa con lista vacía si es null
            .role(usuarioDto.getRole())
            .build();
    }
    
  
   public UsuarioDto mapToUsuarioDto(Usuario usuario) {
        return UsuarioDto.builder()
            .id(usuario.getId())
            .nombreUsuario(usuario.getNombreUsuario())
            .contrasena(usuario.getContrasena())
            .nombre(usuario.getNombre())
            .apellido(usuario.getApellido())
            .email(usuario.getEmail())
            .fotoPerfil(usuario.getFotoPerfil())
            .telefono(usuario.getTelefono())
            .role(usuario.getRole())
            .servicios(usuario.getServicios() != null ? new ArrayList<>(usuario.getServicios()) : new ArrayList<>()) // Inicializa con lista vacía si es null
            .descripcion(usuario.getDescripcion())
            .ordenes(usuario.getOrdenes() != null ? new ArrayList<>(usuario.getOrdenes()) : new ArrayList<>()) // Inicializa con lista vacía si es null
            .build();
    }
    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByNombreUsuario(username);
    }

    @Override
    public void saveUsuario(AuthRequestDto usuario) {
        Usuario user = new Usuario();
        user.setNombreUsuario(usuario.getNombreUsuario());
        user.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        user.setRole("USER");
        user.setServicios(new ArrayList<>());
        user.setOrdenes(new ArrayList<>());
        user.setNombre(usuario.getNombre());
        user.setApellido(usuario.getApellido());
        user.setEmail(usuario.getEmail());
        user.setTelefono(usuario.getTelefono());
        user.setFotoPerfil(usuario.getFotoPerfil());
        user.setDescripcion(usuario.getDescripcion());
        usuarioRepository.save(user);
    }

    @Override
    public void addServiceToUser(String nombreUsuario, Servicio servicio) {
        // Busca el usuario en la base de datos
        Usuario user = usuarioRepository.findByNombreUsuario(nombreUsuario);
        
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Inicializa la lista de servicios si está nula
        if (user.getServicios() == null) {
            user.setServicios(new ArrayList<>());
        }

        // Agrega el nuevo servicio a la lista de servicios del usuario
        user.getServicios().add(servicio);

        // Guarda el usuario actualizado en la base de datos
        usuarioRepository.save(user);
    }

    @Override
    public UsuarioDto findUsuarioByNombre(String nombreUsuario) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        // Método para convertir Usuario a UsuarioDto
        return mapToUsuarioDto(usuario);
    }
    @Override
    public List<Servicio> getServiciosAsociados(String nombreUsuario) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);
        // Retorna la lista de servicios asociados al usuar                              
        return usuario.getServicios(); 
    }



}



   
