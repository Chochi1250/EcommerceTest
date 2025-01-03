package com.ChangaYa.TP_POOAv.controller;

import com.ChangaYa.TP_POOAv.dto.AuthRequestDto;
import com.ChangaYa.TP_POOAv.model.Usuario;
import com.ChangaYa.TP_POOAv.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    

    @Autowired
    private  UsuarioService userService;

   
    public AuthController(UsuarioService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        AuthRequestDto user = new AuthRequestDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Validated @ModelAttribute("user") AuthRequestDto user,
                       BindingResult result, Model model) {
   
    Usuario existingUserUsername = userService.findByUsername(user.getNombreUsuario());
    if (existingUserUsername != null && existingUserUsername.getNombreUsuario() != null) {
        model.addAttribute("user", user);
        model.addAttribute("fail", true); 
        return "register";
    }
    // Si hay errores de validación
    if (result.hasErrors()) {
        model.addAttribute("user", user);
        return "register";
    }
    userService.saveUsuario(user);
    return "redirect:/login"; // Redirige sólo si la creación es exitosa
    }
    

}
