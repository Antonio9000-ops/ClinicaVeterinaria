package com.veterinaria.persistence.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name="Mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_mascota")

    private Integer idMascota;
    private String nombre;
    private String especie;
    private String raza;

    @Column(name="fecha_nacimiento")
    private LocalDate fechaNacimiento;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @ManyToOne  //De muchos a uno.
    @JoinColumn(name="id_propietario", insertable = false, updatable = false)  //une la tabla proietario con la tabla mascotas
    private Propietario propietario;
}
