package com.veterinaria.demo.domain.mapper;

import com.veterinaria.demo.domain.dto.PropietarioDTO;
import com.veterinaria.demo.persistence.entity.Propietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropietarioMapper {

    PropietarioDTO toPropietarioDTO(Propietario propietario);

    // Ignoramos el password al mapear de DTO a Entidad para no sobreescribirlo
    @Mapping(target = "password", ignore = true)
    Propietario toPropietario(PropietarioDTO propietarioDTO);
}