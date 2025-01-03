package com.ChangaYa.TP_POOAv.controller;




import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.ChangaYa.TP_POOAv.dto.ServicioDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ChangaYa.TP_POOAv.service.ServicioService;



@Controller
public class CartController {

    private ServicioService servicioService;

    
    public CartController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/cart/{servicioTitulo}")
    public String getDetallesServicio(@PathVariable("servicioTitulo") String servicioTitulo, Model model) {
        ServicioDto servicioDto = servicioService.findServicioByTitulo(servicioTitulo);
        model.addAttribute("servicio", servicioDto);
        return "cart";
    }
}