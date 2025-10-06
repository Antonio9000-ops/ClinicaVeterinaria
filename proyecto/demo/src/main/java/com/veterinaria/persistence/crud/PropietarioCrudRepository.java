package com.veterinaria.persistence.crud;

import com.veterinaria.persistence.entity.Propietario;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface PropietarioCrudRepository extends CrudRepository<Propietario, Integer> {
    Optional<Propietario> findByEmail(String email);
}
