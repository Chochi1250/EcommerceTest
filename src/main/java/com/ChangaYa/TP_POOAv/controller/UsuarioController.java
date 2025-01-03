package com.ChangaYa.TP_POOAv.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.ChangaYa.TP_POOAv.dto.UsuarioDto;
import com.ChangaYa.TP_POOAv.model.Orden;
import com.ChangaYa.TP_POOAv.model.Servicio;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import com.ChangaYa.TP_POOAv.service.OrdenService;
import com.ChangaYa.TP_POOAv.service.UsuarioService;

import jakarta.validation.Valid;



@Controller
public class UsuarioController {
    private UsuarioService usuarioService;
    private OrdenService ordenService;

    
    public UsuarioController(UsuarioService usuarioService, OrdenService ordenService) {
        this.usuarioService = usuarioService;
        this.ordenService = ordenService;
    }

    //GET - Mostrar Usuarios - Deprecated
    @GetMapping("/usuarios")
    public String listUsuarios(Model model){
        List<UsuarioDto> usuarios = usuarioService.findAllUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarios-list";
    }
    //VIEW - Vista editar perfil
    @GetMapping("/editarPerfil/{nombreUsuario}")
    public String getEditarPerfil(Model model, Principal principal) {
        String nombreUsuario = principal.getName(); 
        UsuarioDto usuarioDto = usuarioService.findUsuarioByNombre(nombreUsuario);
        model.addAttribute("usuario", usuarioDto);
        return "editar-perfil";
    }

    //UPDATE - actualizar perfil
    
    @PostMapping("/editarPerfil/{nombreUsuario}")
    public String actualizarPerfil(@PathVariable("nombreUsuario") String nombreUsuario, 
    @Valid @ModelAttribute("usuario") UsuarioDto usuarioDto,
    BindingResult resultado, Model model) {
    // Qué hacer si hay errores
    if (resultado.hasErrors()) {
        return "editar-perfil";
    }

    // Obtener el usuario existente
    UsuarioDto existingUsuario = usuarioService.findUsuarioByNombre(nombreUsuario);
    if (existingUsuario == null) {
        model.addAttribute("error", "Usuario no encontrado");
        return "editar-perfil";
    }

    // Actualizar solo los atributos permitidos
    existingUsuario.setFotoPerfil(usuarioDto.getFotoPerfil());
    existingUsuario.setNombre(usuarioDto.getNombre());
    existingUsuario.setApellido(usuarioDto.getApellido());
    existingUsuario.setEmail(usuarioDto.getEmail());
    existingUsuario.setTelefono(usuarioDto.getTelefono());
    existingUsuario.setDescripcion(usuarioDto.getDescripcion());
    
    // Llamada al servicio para actualizar el usuario en la base de datos
    usuarioService.updateUsuario(existingUsuario);

    return "redirect:/perfil";
}

    //VIEW - Vista perfil
   @GetMapping("/perfil")
    public String getPerfil(Model model, Principal principal) {
    String nombreUsuario = principal.getName(); 
    UsuarioDto usuarioDto = usuarioService.findUsuarioByNombre(nombreUsuario);
    model.addAttribute("usuario", usuarioDto);
    
    // Obtiene servicios asociados al usuario por nombre de usuario
    List<Servicio> servicios = usuarioService.getServiciosAsociados(nombreUsuario);
    model.addAttribute("servicios", servicios);

    // Obtiene todas las órdenes del usuario
    List<Orden> ordenes = ordenService.findOrdenesByUsuario(nombreUsuario);

    List<Orden> ordenesComoCliente = ordenes.stream()
            .filter(orden -> orden.getUsuarioCliente().equals(nombreUsuario))
            .collect(Collectors.toList());
    
    
    List<Orden> ordenesComoFreelancer = ordenes.stream()
            .filter(orden -> orden.getUsuarioFreelancer().equals(nombreUsuario))
            .collect(Collectors.toList());


    model.addAttribute("ordenesComoCliente", ordenesComoCliente);
    model.addAttribute("ordenesComoFreelancer", ordenesComoFreelancer);
    
    return "perfil";
    
   
}
}