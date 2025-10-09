package com.veterinaria.demo.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // Anotación importante para forzar la configuración
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Rutas que deben ser públicas (visibles sin iniciar sesión)
        final String[] publicUrls = new String[] {
                // Recursos estáticos (diseño)
                "/css/**", "/js/**", "/img/**", "/lib/**", "/images/**", "/scss/**",

                // Vistas públicas mapeadas en ViewController
                "/", "/index", "/about", "/contact", "/feature", "/project", "/service", "/team", "/testimonial", "/404",

                // Autenticación
                "/login", "/register", "/auth/register-web", "/auth/register"
        };


        http
                .csrf(AbstractHttpConfigurer::disable)

                // INICIO DEL ÚNICO BLOQUE DE AUTORIZACIÓN
                .authorizeHttpRequests(authz -> authz

                        // 1. Permite acceso a TODAS las URLs listadas en 'publicUrls' (incluyendo / y /index)
                        .requestMatchers(publicUrls).permitAll()

                        // 2. CUALQUIER OTRA petición SÍ requiere autenticación (siempre al final)
                        .anyRequest().authenticated()
                )
                // FIN DEL ÚNICO BLOQUE DE AUTORIZACIÓN

                // CONFIGURAR el formulario de login
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true) // Redirigir a /index después del login exitoso
                        .permitAll()
                )
                // CONFIGURAR el logout
                .logout(logout -> logout
                        // Usamos AntPathRequestMatcher para la ruta de logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        // *** CORRECCIÓN: Redirigir al index (ruta raíz) después de cerrar sesión ***
                        .logoutSuccessUrl("/")
                        // Configuraciones adicionales de seguridad en el logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}