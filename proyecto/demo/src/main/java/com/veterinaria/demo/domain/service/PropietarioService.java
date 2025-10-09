package com.veterinaria.demo.domain.service;

import com.veterinaria.demo.domain.dto.PropietarioDTO;
import java.util.Optional;

public interface PropietarioService {
    Optional<PropietarioDTO> getPropietarioByEmail(String email);
    PropietarioDTO updatePropietario(String email, PropietarioDTO propietarioDetails);
}