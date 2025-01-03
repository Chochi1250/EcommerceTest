package com.ChangaYa.TP_POOAv.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ChangaYa.TP_POOAv.dto.ServicioDto;
import com.ChangaYa.TP_POOAv.model.EstadoServicio;
import com.ChangaYa.TP_POOAv.model.Servicio;
import com.ChangaYa.TP_POOAv.model.Usuario;
import com.ChangaYa.TP_POOAv.service.ServicioService;
import com.ChangaYa.TP_POOAv.service.UsuarioService;
import com.ChangaYa.TP_POOAv.service.ReviewService;
import com.ChangaYa.TP_POOAv.dto.ReviewDto;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.bson.types.ObjectId;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;







@Controller
public class ServicioController {
    
    private ServicioService servicioService;

    private UsuarioService usuarioService;
    
    private ReviewService reviewService;
    
    public ServicioController(ServicioService servicioService, UsuarioService usuarioService, ReviewService reviewService) {
        this.servicioService = servicioService;
        this.usuarioService = usuarioService;
        this.reviewService = reviewService;
    }
    
    
    //VIEW
    @GetMapping("/servicios")
    public String listServicios(Model model) {

    String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
    
    
    List<ServicioDto> servicios = servicioService.findAllServicios().stream()
        .filter(servicio -> !servicio.getIdFreeLancer().equals(nombreUsuario) && servicio.getEstadoServicio() == EstadoServicio.Disponible)
        
        .toList();

    model.addAttribute("servicios", servicios);
    return "servicios";
}

    //VIEW
    @GetMapping("/servicios/new")
    public String createServicioForm(Model model) {
        Servicio servicio = new Servicio();
        model.addAttribute("servicio", servicio);
        return "crear-servicios";
    }

    //POST
    @PostMapping("/servicios/new")
    public String guardarServicio(@Valid @ModelAttribute("servicio") ServicioDto servicioDto,
                              BindingResult resultado, Model model) {
    if (resultado.hasErrors()) {
        model.addAttribute("servicio", servicioDto);
        return "crear-servicios";
    }
    String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
    Usuario usuarioAutenticado = usuarioService.findByUsername(nombreUsuario);
    if (usuarioAutenticado == null) {
        throw new RuntimeException("Usuario autenticado no encontrado");
    }
    servicioDto.setIdFreeLancer(usuarioAutenticado.getNombreUsuario());
    servicioDto.setEstadoServicio(EstadoServicio.Disponible);
    Servicio servicioGuardado = servicioService.saveServicio(servicioDto);
    usuarioService.addServiceToUser(nombreUsuario, servicioGuardado);
    return "redirect:/servicios"; 
}


    
    //VIEW
    @GetMapping("/servicios/{servicioTitulo}")
    public String getDetallesServicio(@PathVariable("servicioTitulo") String servicioTitulo, Model model) {
        
        ServicioDto servicioDto = servicioService.findServicioByTitulo(servicioTitulo);
        List<ReviewDto> reviews = reviewService.getReviewsAsociadas(servicioTitulo);
        model.addAttribute("servicio", servicioDto);
        model.addAttribute("reviews", reviews);
        return "detalles-servicio";
    }

    
    
    //VIEW
    @GetMapping("/editar/{servicioTitulo}")
    public String actualizarServicioForm(@PathVariable("servicioTitulo") String servicioTitulo, Model model) {
        ServicioDto servicioDto = servicioService.findServicioByTitulo(servicioTitulo);
        model.addAttribute("servicio", servicioDto);
        return "editar-servicios";
    }
    
    //UPDATE
    @PostMapping("/editar/{servicioTitulo}")
    public String actualizarServicio(@PathVariable("servicioTitulo") String servicioTitulo, 
        @Valid @ModelAttribute("servicio") ServicioDto servicioDto,
        BindingResult resultado, Model model) 
        {
        //Que hacer si hay errores
        if (resultado.hasErrors()) 
        {
            System.out.println("error2");
            return "editar-servicios";
        }  
        // Obtener el servicio existente
        ServicioDto existingServicio = servicioService.findServicioByTitulo(servicioTitulo);
        if (existingServicio == null) 
        {
            model.addAttribute("error", "Servicio no encontrado");
            System.out.println("error");
            return "editar-servicios";
            
        }
            // Actualizar solo los atributos permitidos
            existingServicio.setDescripcion(servicioDto.getDescripcion());
            existingServicio.setPrecio(servicioDto.getPrecio());
            existingServicio.setEstadoServicio(servicioDto.getEstadoServicio());
            if (servicioDto.getCategorias() != null) {
                existingServicio.getCategorias().clear();
                existingServicio.getCategorias().addAll(servicioDto.getCategorias());
            }
            existingServicio.setReviews(servicioDto.getReviews());
            existingServicio.setPhotoUrl(servicioDto.getPhotoUrl());
            existingServicio.setDuracionDias(servicioDto.getDuracionDias());

            // Llamada al servicio para actualizar el servicio en la base de datos
            servicioService.updateServicio(existingServicio);
            System.out.println("Servicio actualizado");
            return "redirect:/perfil";
        }
    

    //DELETE - SERVICE
    @GetMapping("/servicios/{servicioId}/eliminar")
    public String eliminarServicio(@PathVariable("servicioId") ObjectId servicioId) {
        servicioService.deleteServicio(servicioId);
        return "redirect:/servicios";
    }
    
    //SEARCHBAR
    @GetMapping("/servicios/buscar")
    public String buscarServicios(@RequestParam("query") String query, Model model) {
        List<ServicioDto> servicios = servicioService.searchServicios(query);
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        servicios = servicios.stream()
            .filter(servicio -> !servicio.getIdFreeLancer().equals(nombreUsuario) && servicio.getEstadoServicio() == EstadoServicio.Disponible)
            .toList();
        model.addAttribute("servicios", servicios);
        return "servicios";
    }
    
    

}
