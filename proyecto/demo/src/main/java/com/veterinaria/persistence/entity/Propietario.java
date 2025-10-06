package com.veterinaria.persistence.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity //clase que es una entidad y debe ser mapeada en la base de datos

@Table(name="propietarios") //especifica a que tabla corresponde en la base de datos
public class Propietario {
    @Id //clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) //esto configura como se genera la clave primaria y indica que se autoincrementa
    @Column(name= "id_propietario") //mapea el campo de la columna de la llave primaria

    private Integer idPropietario;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String email;
    private String password;

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @OneToMany(mappedBy = "propietario") //Indica la relacion de uno a muchos. un propietario puede tener muchas mascotas
    private List<Mascota> mascotas;

}
