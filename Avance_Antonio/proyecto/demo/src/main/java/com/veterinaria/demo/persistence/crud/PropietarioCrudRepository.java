package com.veterinaria.demo.persistence.crud;

//Interfaz generica que proporciona los metodos crud
import org.springframework.data.repository.CrudRepository;

import com.veterinaria.demo.persistence.entity.Propietario;

import java.util.Optional;

//Es una interfaz no una clase que maneja operaciones Crud
public interface PropietarioCrudRepository extends CrudRepository<Propietario, Integer> {
    Optional<Propietario> findByEmail(String email);
}
