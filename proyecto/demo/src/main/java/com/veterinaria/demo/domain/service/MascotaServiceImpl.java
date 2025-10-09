package com.veterinaria.demo.domain.service;

import com.veterinaria.demo.domain.dto.MascotaDTO;
import com.veterinaria.demo.domain.mapper.MascotaMapper;
import com.veterinaria.demo.persistence.crud.MascotaCrudRepository;
import com.veterinaria.demo.persistence.crud.PropietarioCrudRepository;
import com.veterinaria.demo.persistence.entity.Mascota;
import com.veterinaria.demo.persistence.entity.Propietario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaServiceImpl implements MascotaService {

    private final MascotaCrudRepository mascotaCrudRepository;
    private final PropietarioCrudRepository propietarioCrudRepository;
    private final MascotaMapper mapper;

    public MascotaServiceImpl(MascotaCrudRepository mascotaCrudRepository, PropietarioCrudRepository propietarioCrudRepository, MascotaMapper mapper) {
        this.mascotaCrudRepository = mascotaCrudRepository;
        this.propietarioCrudRepository = propietarioCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<MascotaDTO> getMascotasByPropietarioEmail(String email) {
        // Buscamos al propietario por su email para obtener su ID
        return propietarioCrudRepository.findByEmail(email)
                .map(propietario -> {
                    // Usamos el ID del propietario para buscar sus mascotas
                    List<Mascota> mascotas = mascotaCrudRepository.findAllByPropietarioIdPropietario(propietario.getIdPropietario());
                    return mapper.toMascotasDTO(mascotas);
                }).orElse(List.of()); // Si no se encuentra el propietario, devuelve una lista vacía
    }

    @Override
    public Optional<MascotaDTO> getMascotaById(Integer idMascota) {
        return mascotaCrudRepository.findById(idMascota).map(mapper::toMascotaDTO);
    }

    @Override
    public MascotaDTO saveMascota(MascotaDTO mascotaDTO, String propietarioEmail) {
        // Buscamos la entidad Propietario para asociarla a la mascota
        Propietario propietario = propietarioCrudRepository.findByEmail(propietarioEmail)
                .orElseThrow(() -> new RuntimeException("Propietario no encontrado"));

        Mascota mascota = mapper.toMascota(mascotaDTO);
        mascota.setPropietario(propietario); // ¡Paso clave! Asignamos el propietario a la mascota

        return mapper.toMascotaDTO(mascotaCrudRepository.save(mascota));
    }

    @Override
    public boolean deleteMascota(Integer idMascota) {
        return getMascotaById(idMascota).map(mascota -> {
            mascotaCrudRepository.deleteById(idMascota);
            return true;
        }).orElse(false);
    }
}