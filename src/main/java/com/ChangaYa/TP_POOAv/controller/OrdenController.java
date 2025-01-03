package com.ChangaYa.TP_POOAv.controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ChangaYa.TP_POOAv.dto.OrdenDto;
import com.ChangaYa.TP_POOAv.dto.ServicioDto;
import com.ChangaYa.TP_POOAv.model.EstadoOrden;
import com.ChangaYa.TP_POOAv.model.Orden;
import com.ChangaYa.TP_POOAv.model.Usuario;
import com.ChangaYa.TP_POOAv.service.OrdenService;
import com.ChangaYa.TP_POOAv.service.ServicioService;
import com.ChangaYa.TP_POOAv.service.UsuarioService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.bson.types.ObjectId;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrdenController {

    private final OrdenService ordenService;
    private final UsuarioService usuarioService;
    private final ServicioService servicioService;

    public OrdenController(OrdenService ordenService, UsuarioService usuarioService, ServicioService servicioService) {
        this.ordenService = ordenService;
        this.usuarioService = usuarioService;
        this.servicioService = servicioService;
    }

    // VIEW: Mostrar todas las órdenes
    @GetMapping("/ordenes")
    public String listOrdenes(Model model) {
        List<OrdenDto> ordenes = ordenService.findAllOrdenes();
        model.addAttribute("ordenes", ordenes);
        return "ordenes";
    }

    // VIEW: Formulario para crear una nueva orden
    @GetMapping("/ordenes/new")
    public String createOrdenForm(Model model) {
        Orden ordenDto = new Orden();
        model.addAttribute("orden", ordenDto);
        return "servicios";
    }

    // POST: Guardar nueva orden
    @PostMapping("/ordenes/new")
    public String guardarOrden(
        @RequestParam("servicioId") ObjectId servicioId, Model model) {

    // Obtener el nombre del usuario autenticado y su ID
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLogueado = usuarioService.findByUsername(nombreUsuario);
        if (usuarioLogueado == null) {
            throw new RuntimeException("Usuario autenticado no encontrado");
        }
        String idCliente = usuarioLogueado.getNombreUsuario();

    // Obtener el servicio por ID para extraer idFreelancer y otros datos
        ServicioDto servicio = servicioService.findServicioById(servicioId);
        if (servicio == null) {
            throw new RuntimeException("Servicio no encontrado");
        }
        String idFreelancer = servicio.getIdFreeLancer();
        System.out.println(idFreelancer);


    // Crear el objeto OrdenDto con los valores obtenidos
        OrdenDto ordenDto = new OrdenDto();
        ordenDto.setUsuarioCliente(idCliente);
        ordenDto.setTituloServicio(servicio.getTitulo());
        ordenDto.setPrecioServicio(servicio.getPrecio());
        ordenDto.setUsuarioFreelancer(idFreelancer);
        ordenDto.setFechaOrden(LocalDate.now());
        ordenDto.setEstadoOrden(EstadoOrden.En_Proceso);  // Establece el estado inicial de la orden
        ordenDto.setFechaEntrega(LocalDate.now().plusDays(servicio.getDuracionDias())); // Ejemplo de fecha de entrega

    // Guardar la orden en la base de datos usando `ordenService`
        ordenService.saveOrden(ordenDto);
        try {
            Thread.sleep(3000); // Espera de 3 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Manejar la excepción si es necesario
        }
        return "redirect:/success"; // Redirige a la página de exito
    }


    // VIEW: Detalles de una orden específica
    @GetMapping("/ordenes/{ordenId}")
    public String getDetallesOrden(@PathVariable("ordenId") ObjectId ordenId, Model model) {
        OrdenDto ordenDto = ordenService.findOrdenById(ordenId);
        model.addAttribute("orden", ordenDto);
        return "detalles-orden";
    }

    
    // POST: Actualizar una orden
    @PostMapping("/ordenes/{ordenId}/actualizar")
    public String actualizarEstadoOrden(@PathVariable("ordenId") ObjectId ordenId, @RequestParam(value = "estado", required = false) EstadoOrden estado, Model model) {
        OrdenDto ordenDto = ordenService.findOrdenById(ordenId);
        if (ordenDto == null) {
            System.out.println("Orden no encontrada");
            throw new RuntimeException("Orden no encontrada");
            
        }
        ordenDto.setEstadoOrden(EstadoOrden.Terminado);
        ordenService.saveOrden(ordenDto);
        System.out.println("Orden actualizada");
        try {
            Thread.sleep(3000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "redirect:/perfil";
    }

    // DELETE: Eliminar una orden

    // VIEW: Página de éxito
    @GetMapping("/success")
    public String successPage() {
        return "success";
    }
}
