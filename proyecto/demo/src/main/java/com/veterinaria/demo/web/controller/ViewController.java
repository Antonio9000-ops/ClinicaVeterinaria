package com.veterinaria.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.veterinaria.demo.persistence.entity.Propietario;

@Controller
public class ViewController {

    // Muestra la página de login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        // Esta línea es la que resuelve el error.
        // Crea un objeto Propietario vacío y lo pone a disposición de la plantilla HTML.
        model.addAttribute("propietario", new Propietario());
        return "register"; // Devuelve el nombre del archivo register.html
    }

    // Muestra la página de inicio después del login
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}