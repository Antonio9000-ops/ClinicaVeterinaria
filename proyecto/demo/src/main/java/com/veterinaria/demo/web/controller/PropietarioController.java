package com.veterinaria.demo.web.controller;

import com.veterinaria.demo.domain.dto.PropietarioDTO;
import com.veterinaria.demo.domain.service.PropietarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cuenta")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @GetMapping
    public String verCuenta(Authentication authentication, Model model) {
        // Authentication.getName() devuelve el "username", que en nuestro caso es el email
        String email = authentication.getName();

        propietarioService.getPropietarioByEmail(email).ifPresent(propietario -> {
            model.addAttribute("propietario", propietario);
        });

        return "cuenta"; // Devuelve el nombre de la vista cuenta.html
    }

    @PostMapping("/actualizar")
    public String actualizarCuenta(@ModelAttribute PropietarioDTO propietarioDTO, Authentication authentication) {
        String email = authentication.getName();
        propietarioService.updatePropietario(email, propietarioDTO);

        // Redirigimos a la misma página con un parámetro para mostrar un mensaje de éxito
        return "redirect:/cuenta?exito";
    }
}