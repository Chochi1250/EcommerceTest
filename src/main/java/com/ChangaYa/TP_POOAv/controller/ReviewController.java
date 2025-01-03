package com.ChangaYa.TP_POOAv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.ChangaYa.TP_POOAv.dto.ReviewDto;
import jakarta.validation.Valid;
import com.ChangaYa.TP_POOAv.service.ReviewService;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;

@Controller
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }



    @PostMapping("crearReview")
    public String crearReview(@Valid @ModelAttribute("review") ReviewDto reviewDto) {
    
    String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
    String tituloServicio = reviewDto.getTituloServicio();
    reviewDto.setTituloServicio(tituloServicio);
    //Debug, para ver si lo muestra como nulo o no
    System.out.println("El nombre del servicio es " + reviewDto.getTituloServicio());
    reviewDto.setNombreCliente(nombreUsuario);
    reviewDto.setFechaCalificacion(LocalDateTime.now().toLocalDate());
    reviewService.saveReview(reviewDto);
    try {
        Thread.sleep(3000); // Espera de 3 segundos
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        // Manejar la excepción si es necesario
    }
    return "redirect:/perfil";
    }
}
