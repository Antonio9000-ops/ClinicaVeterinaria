package com.veterinaria.demo.persistence.crud;

import org.springframework.data.repository.CrudRepository;

import com.veterinaria.demo.persistence.entity.Mascota;

public interface MascotaCrudRepository extends CrudRepository<Mascota, Integer> {
}