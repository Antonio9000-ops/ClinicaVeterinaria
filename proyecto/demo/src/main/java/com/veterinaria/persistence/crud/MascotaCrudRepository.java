package com.veterinaria.persistence.crud;

import com.veterinaria.persistence.entity.Mascota;
import org.springframework.data.repository.CrudRepository;

public interface MascotaCrudRepository extends CrudRepository<Mascota, Integer> {
}