package com.veterinaria.demo.web.controller;

import org.springframework.web.bind.annotation.*;

import com.veterinaria.demo.persistence.entity.Mascota;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    // Endpoint para que un propietario autenticado registre una mascota
    @PostMapping("/registrar")
    public Mascota registrarMascota(@RequestBody Mascota mascota) {
        // Aquí iría la lógica para guardar la mascota asociada al propietario
        // que ha iniciado sesión.
        return mascota; // Devuelve la mascota guardada
    }
}