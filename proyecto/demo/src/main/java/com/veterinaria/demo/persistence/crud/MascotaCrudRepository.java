package com.veterinaria.demo.persistence.crud;

import com.veterinaria.demo.persistence.entity.Mascota;
import org.springframework.data.repository.CrudRepository;

import java.util.List; // Importar List

// Interfaz genérica que proporciona los métodos CRUD
public interface MascotaCrudRepository extends CrudRepository<Mascota, Integer> {

    // Spring Data JPA creará automáticamente la consulta basándose en el nombre del método
    // Busca todas las Mascotas cuyo atributo 'propietario' tenga un 'idPropietario' que coincida
    List<Mascota> findAllByPropietarioIdPropietario(Integer idPropietario);
}