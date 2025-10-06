package com.veterinaria.web.controller;

import com.veterinaria.persistence.crud.PropietarioCrudRepository;
import com.veterinaria.persistence.entity.Propietario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PropietarioCrudRepository propietarioCrudRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Propietario> registrarPropietario(@RequestBody Propietario propietario) {
        // 1. Cifrar la contraseña antes de guardarla
        propietario.setPassword(passwordEncoder.encode(propietario.getPassword()));

        // 2. Guardar el nuevo propietario en la base de datos
        Propietario propietarioGuardado = propietarioCrudRepository.save(propietario);

        // 3. Devolver el propietario guardado con un código de estado 201 Created
        return new ResponseEntity<>(propietarioGuardado, HttpStatus.CREATED);
    }

    // El endpoint de login no es necesario aquí, Spring Security lo maneja internamente
}