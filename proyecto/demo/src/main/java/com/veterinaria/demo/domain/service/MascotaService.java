package com.veterinaria.demo.domain.service;

import com.veterinaria.demo.domain.dto.MascotaDTO;
import java.util.List;
import java.util.Optional;

public interface MascotaService {
    List<MascotaDTO> getMascotasByPropietarioEmail(String email);
    Optional<MascotaDTO> getMascotaById(Integer idMascota);
    MascotaDTO saveMascota(MascotaDTO mascota, String propietarioEmail);
    boolean deleteMascota(Integer idMascota);
}