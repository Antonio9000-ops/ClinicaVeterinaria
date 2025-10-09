package com.veterinaria.demo.web.controller;

import com.veterinaria.demo.domain.dto.MascotaDTO;
import com.veterinaria.demo.domain.service.MascotaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // 1. Mostrar la lista de mascotas del propietario
    @GetMapping
    public String listarMascotas(Authentication authentication, Model model) {
        String email = authentication.getName();
        List<MascotaDTO> mascotas = mascotaService.getMascotasByPropietarioEmail(email);
        model.addAttribute("mascotas", mascotas);
        return "mis-mascotas"; // Devuelve la vista mis-mascotas.html
    }

    // 2. Mostrar el formulario para una nueva mascota
    @GetMapping("/nueva")
    public String formularioNuevaMascota(Model model) {
        model.addAttribute("mascota", new MascotaDTO());
        model.addAttribute("titulo", "Registrar Nueva Mascota");
        return "mascota-form"; // Devuelve la vista mascota-form.html
    }

    // 3. Mostrar el formulario para editar una mascota existente
    @GetMapping("/editar/{id}")
    public String formularioEditarMascota(@PathVariable("id") Integer idMascota, Model model) {
        mascotaService.getMascotaById(idMascota).ifPresent(mascota -> {
            model.addAttribute("mascota", mascota);
        });
        model.addAttribute("titulo", "Editar Mascota");
        return "mascota-form"; // Reutilizamos la misma vista del formulario
    }

    // 4. Procesar el guardado (creación o actualización)
    @PostMapping("/guardar")
    public String guardarMascota(@ModelAttribute MascotaDTO mascota, Authentication authentication) {
        String email = authentication.getName();
        mascotaService.saveMascota(mascota, email);
        return "redirect:/mascotas"; // Redirige a la lista de mascotas
    }

    // 5. Procesar la eliminación
    @GetMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable("id") Integer idMascota) {
        mascotaService.deleteMascota(idMascota);
        return "redirect:/mascotas"; // Redirige a la lista de mascotas
    }
}