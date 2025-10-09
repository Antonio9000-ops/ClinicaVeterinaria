package com.veterinaria.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.veterinaria.demo.persistence.entity.Propietario;

@Controller
@RequestMapping("/")
public class ViewController {


    @GetMapping({"/index"}) // ¡Añadimos /index aquí!
    public String showIndexPage() {
        return "index"; // Carga index.html
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Carga login.html
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("propietario", new Propietario());
        return "register"; // Carga register.html
    }

    // --- Mapeos de otras páginas de la plantilla ---

    @GetMapping("/404")
    public String show404Page() {
        return "404"; // Carga 404.html
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about"; // Carga about.html
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact"; // Carga contact.html
    }

    @GetMapping("/feature")
    public String showFeaturePage() {
        return "feature"; // Carga feature.html
    }

    @GetMapping("/project")
    public String showProjectPage() {
        return "project"; // Carga project.html
    }

    @GetMapping("/service")
    public String showServicePage() {
        return "service"; // Carga service.html
    }

    @GetMapping("/team")
    public String showTeamPage() {
        return "team"; // Carga team.html
    }

    @GetMapping("/testimonial")
    public String showTestimonialPage() {
        return "testimonial"; // Carga testimonial.html
    }
}
