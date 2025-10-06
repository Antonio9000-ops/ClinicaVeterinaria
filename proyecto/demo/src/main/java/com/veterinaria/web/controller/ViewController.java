package com.veterinaria.web.controller;

import com.veterinaria.persistence.entity.Propietario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // Muestra la página de login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Devuelve el nombre del archivo login.html
    }

    // Muestra la página de registro
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        // Creamos un objeto Propietario vacío para que el formulario pueda enlazar sus campos
        model.addAttribute("propietario", new Propietario());
        return "register"; // Devuelve el nombre del archivo register.html
    }

    // Muestra la página de inicio después del login
    @GetMapping("/home")
    public String showHomePage() {
        return "home"; // Devuelve el nombre del archivo home.html
    }
}