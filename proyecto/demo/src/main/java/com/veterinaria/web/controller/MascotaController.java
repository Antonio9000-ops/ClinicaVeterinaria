package com.veterinaria.web.controller;

import com.veterinaria.persistence.entity.Mascota;
import org.springframework.web.bind.annotation.*;

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