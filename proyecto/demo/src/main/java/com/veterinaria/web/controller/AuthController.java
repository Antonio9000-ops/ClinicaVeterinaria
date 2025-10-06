package com.veterinaria.web.controller;

import com.veterinaria.persistence.crud.PropietarioCrudRepository;
import com.veterinaria.persistence.entity.Propietario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Cambiamos a @Controller porque ahora manejamos vistas (redirecciones)
// y respuestas de API (@ResponseBody)
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PropietarioCrudRepository propietarioCrudRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint para la API (ej. Postman).
     * Devuelve datos JSON en el cuerpo de la respuesta.
     */
    @PostMapping("/register")
    @ResponseBody // Indica que la respuesta debe ser JSON, no el nombre de una vista
    public ResponseEntity<Propietario> registrarPropietario(@RequestBody Propietario propietario) {
        propietario.setPassword(passwordEncoder.encode(propietario.getPassword()));
        Propietario propietarioGuardado = propietarioCrudRepository.save(propietario);
        return new ResponseEntity<>(propietarioGuardado, HttpStatus.CREATED);
    }

    /**
     * Endpoint para el formulario web.
     * Procesa los datos y redirige a otra página.
     */
    @PostMapping("/register-web")
    public String registrarPropietarioDesdeWeb(@ModelAttribute Propietario propietario) {
        // La lógica es la misma: cifrar y guardar
        propietario.setPassword(passwordEncoder.encode(propietario.getPassword()));
        propietarioCrudRepository.save(propietario);

        // Redirigimos al usuario a la página de login con un parámetro de éxito
        return "redirect:/login?success";
    }
}