package com.veterinaria.demo.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Anotación importante para forzar la configuración
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login", "/register", "/auth/register-web").permitAll()
                        // PERMITIR acceso a la API de registro (si aún la quieres pública)
                        .requestMatchers("/auth/register").permitAll()
                        // CUALQUIER OTRA petición requiere autenticación
                        .anyRequest().authenticated()
                )
                // CONFIGURAR el formulario de login
                .formLogin(form -> form
                        .loginPage("/login") // La página de login personalizada está en /login
                        .defaultSuccessUrl("/home", true) // Redirigir a /home después de un login exitoso
                        .permitAll() // Permitir a todos ver la página de login
                )
                // CONFIGURAR el logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout") // Redirigir a la página de login después de cerrar sesión
                        .permitAll()
                );

        return http.build();
    }
}