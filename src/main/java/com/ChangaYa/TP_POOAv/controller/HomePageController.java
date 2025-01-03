package com.ChangaYa.TP_POOAv.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ChangaYa.TP_POOAv.dto.ServicioDto;

import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.ChangaYa.TP_POOAv.service.ServicioService;

@Controller
public class HomePageController {

    @Autowired
    private ServicioService servicioService;
    
    private HomePageController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

  
    @GetMapping("/home")
    public String listHomepage(Model model){
        
        List<ServicioDto> servicios = servicioService.findAllServicios();
        model.addAttribute("servicios", servicios);
        return "home-page";
    }
}
