package com.veterinaria.domain.service;

import com.veterinaria.persistence.crud.PropietarioCrudRepository;
import com.veterinaria.persistence.entity.Propietario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    //Este servicio se encarga de recuperar los datos del usuario y validar las credenciales

    @Autowired
    private PropietarioCrudRepository propietarioCrudRepository;

    @Override
    //busca el usuario en la base de datos por medio de su correo
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Propietario propietario = propietarioCrudRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(propietario.getEmail())
                .password(propietario.getPassword())
                .roles("USER")
                .build();
    }
}
