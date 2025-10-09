package com.veterinaria.demo.domain.service;

import com.veterinaria.demo.domain.dto.PropietarioDTO;
import com.veterinaria.demo.domain.mapper.PropietarioMapper;
import com.veterinaria.demo.persistence.crud.PropietarioCrudRepository;
import com.veterinaria.demo.persistence.entity.Propietario;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PropietarioServiceImpl implements PropietarioService {

    private final PropietarioCrudRepository propietarioCrudRepository;
    private final PropietarioMapper mapper;

    // Inyección de dependencias por constructor (práctica recomendada)
    public PropietarioServiceImpl(PropietarioCrudRepository propietarioCrudRepository, PropietarioMapper mapper) {
        this.propietarioCrudRepository = propietarioCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<PropietarioDTO> getPropietarioByEmail(String email) {
        return propietarioCrudRepository.findByEmail(email)
                .map(mapper::toPropietarioDTO);
    }

    @Override
    public PropietarioDTO updatePropietario(String email, PropietarioDTO propietarioDetails) {
        // Buscamos la entidad existente en la base de datos
        Propietario propietario = propietarioCrudRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        // Actualizamos solo los campos permitidos desde el DTO
        propietario.setNombre(propietarioDetails.getNombre());
        propietario.setApellido(propietarioDetails.getApellido());
        propietario.setTelefono(propietarioDetails.getTelefono());
        propietario.setDireccion(propietarioDetails.getDireccion());

        // Guardamos la entidad actualizada y la convertimos de nuevo a DTO para devolverla
        return mapper.toPropietarioDTO(propietarioCrudRepository.save(propietario));
    }
}