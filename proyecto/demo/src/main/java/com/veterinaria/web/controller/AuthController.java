package com.veterinaria.web.controller;

import com.veterinaria.domain.service.UserDetailsServiceImp;
import com.veterinaria.persistence.entity.Propietario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Endpoint para registrar un nuevo propietario
    @PostMapping("/register")
    public Propietario registrarPropietario(@RequestBody Propietario propietario) {
        propietario.setPassword(passwordEncoder.encode(propietario.getPassword()));
        // Aquí deberías guardar el propietario en la base de datos
        // a través de un servicio que use PropietarioCrudRepository
        return propietario; // Devuelve el propietario guardado
    }

    // Endpoint para el login (Spring Security lo maneja, pero puedes tener uno para generar tokens)
    @PostMapping("/login")
    public String login() {
        // La autenticación la gestiona Spring Security.
        // Si llega aquí, es que el login fue exitoso.
        // En un futuro, aquí generarías un JWT (JSON Web Token).
        return "Login exitoso";
    }
}