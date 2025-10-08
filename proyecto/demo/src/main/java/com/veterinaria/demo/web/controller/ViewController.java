package com.veterinaria.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.veterinaria.demo.persistence.entity.Propietario;

@Controller
@RequestMapping("/") // Define un prefijo opcional para todas las rutas
public class ViewController {

    // --- Vistas de Autenticación y Home (Ya existentes) ---

    // Muestra la página de login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Muestra la página de registro y añade el objeto Propietario al modelo
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("propietario", new Propietario());
        return "register";
    }

    // Muestra la página de inicio después del login
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    // La página principal o landing page
    @GetMapping("/")
    public String showIndexPage() {
        return "index"; // Carga index.html
    }

    // La página de errores 404
    @GetMapping("/404")
    public String show404Page() {
        return "404"; // Carga 404.html
    }

    // La página "Acerca de Nosotros"
    @GetMapping("/about")
    public String showAboutPage() {
        return "about"; // Carga about.html
    }

    // La página de contacto
    @GetMapping("/contact")
    public String showContactPage() {
        return "contact"; // Carga contact.html
    }

    // La página de características o servicios destacados
    @GetMapping("/feature")
    public String showFeaturePage() {
        return "feature"; // Carga feature.html
    }

    // La página del proyecto o galería
    @GetMapping("/project")
    public String showProjectPage() {
        return "project"; // Carga project.html
    }

    // La página de servicios
    @GetMapping("/service")
    public String showServicePage() {
        return "service"; // Carga service.html
    }

    // La página de equipo o staff
    @GetMapping("/team")
    public String showTeamPage() {
        return "team"; // Carga team.html
    }

    // La página de testimonios
    @GetMapping("/testimonial")
    public String showTestimonialPage() {
        return "testimonial"; // Carga testimonial.html
    }
}