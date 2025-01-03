package com.ChangaYa.TP_POOAv.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.ChangaYa.TP_POOAv.repository.*;
import com.ChangaYa.TP_POOAv.service.impl.*;
import com.ChangaYa.TP_POOAv.model.*;
import com.ChangaYa.TP_POOAv.dto.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImp usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllUsuarios() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<UsuarioDto> usuarios = usuarioService.findAllUsuarios();

        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUsuario() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombreUsuario("testUser");
        Usuario usuario = new Usuario();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        usuarioService.updateUsuario(usuarioDto);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testFindByNombre() {
        String nombre = "testUser";
        Usuario usuario = new Usuario();
        when(usuarioRepository.findBynombre(nombre)).thenReturn(Optional.of(usuario));

        UsuarioDto usuarioDto = usuarioService.findByNombre(nombre);

        assertNotNull(usuarioDto);
        verify(usuarioRepository, times(1)).findBynombre(nombre);
    }

    @Test
    void testMapToUsuario() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombreUsuario("testUser");

        Usuario usuario = usuarioService.mapToUsuario(usuarioDto);

        assertEquals(usuarioDto.getNombreUsuario(), usuario.getNombreUsuario());
    }

    @Test
    void testMapToUsuarioDto() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("testUser");

        UsuarioDto usuarioDto = usuarioService.mapToUsuarioDto(usuario);

        assertEquals(usuario.getNombreUsuario(), usuarioDto.getNombreUsuario());
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";
        Usuario usuario = new Usuario();
        when(usuarioRepository.findByNombreUsuario(username)).thenReturn(usuario);

        Usuario foundUser = usuarioService.findByUsername(username);

        assertNotNull(foundUser);
        verify(usuarioRepository, times(1)).findByNombreUsuario(username);
    }

    @Test
    void testSaveUsuario() {
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setNombreUsuario("testUser");
        authRequestDto.setContrasena("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        usuarioService.saveUsuario(authRequestDto);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(passwordEncoder, times(1)).encode(authRequestDto.getContrasena());
    }



    @Test
    void testAddServiceToUser() {
        String nombreUsuario = "testUser";
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        Servicio servicio = new Servicio();
        when(usuarioRepository.findByNombreUsuario(nombreUsuario)).thenReturn(usuario);

        usuarioService.addServiceToUser(nombreUsuario, servicio);

        assertTrue(usuario.getServicios().contains(servicio));
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testFindUsuarioByNombre() {
        String nombreUsuario = "testUser";
        Usuario usuario = new Usuario();
        when(usuarioRepository.findByNombreUsuario(nombreUsuario)).thenReturn(usuario);

        UsuarioDto usuarioDto = usuarioService.findUsuarioByNombre(nombreUsuario);

        assertNotNull(usuarioDto);
        verify(usuarioRepository, times(1)).findByNombreUsuario(nombreUsuario);
    }

    @Test
void testGetServiciosAsociados() {
    String nombreUsuario = "testUser";
    Usuario usuario = new Usuario();
    Servicio servicio1 = new Servicio();
    Servicio servicio2 = new Servicio();

    

    //Coniverte explícitamente a ArrayList si es necesario un ArrayList
    usuario.setServicios(new ArrayList<>(Arrays.asList(servicio1, servicio2)));

    when(usuarioRepository.findByNombreUsuario(nombreUsuario)).thenReturn(usuario);

    List<Servicio> servicios = usuarioService.getServiciosAsociados(nombreUsuario);

    assertEquals(2, servicios.size());
    verify(usuarioRepository, times(1)).findByNombreUsuario(nombreUsuario);
}
}

