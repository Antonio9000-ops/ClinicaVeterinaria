package com.veterinaria.demo.domain.mapper;

import com.veterinaria.demo.domain.dto.MascotaDTO;
import com.veterinaria.demo.persistence.entity.Mascota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MascotaMapper {

    @Mappings({
            @Mapping(source = "propietario.idPropietario", target = "idPropietario")
    })
    MascotaDTO toMascotaDTO(Mascota mascota);

    List<MascotaDTO> toMascotasDTO(List<Mascota> mascotas);

    @Mapping(target = "propietario", ignore = true) // Ignoramos el objeto completo, lo asignaremos en el servicio
    Mascota toMascota(MascotaDTO mascotaDTO);
}