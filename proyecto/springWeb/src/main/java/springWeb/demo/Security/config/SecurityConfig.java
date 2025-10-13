package springWeb.demo.Security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import lombok.RequiredArgsConstructor;
import springWeb.demo.Security.jwt.JwtAuthFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthFilter jwtAuthFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                // Rutas públicas - sin autenticación
                                                .requestMatchers("/", "/login", "/index.html", "/home", "/welcome",
                                                                "/navbar",
                                                                "navbar.html",
                                                                "/api/mascotas/**",
                                                                "/register",
                                                                "/login.html", "/*.css", "/*.js", "/*.ico",
                                                                "/css/**", "/js/**", "/images/**", "/inicio",
                                                                "inicio.html", "mascotas.html", "/mascotas",
                                                                "/ver-mascotas", "/contact", "contact.html",
                                                                "/contact-fake",
                                                                "/static/**", "/error")
                                                .permitAll()
                                                .requestMatchers("/auth/**").permitAll()

                                                // Rutas de API protegidas por roles
                                                .requestMatchers("/api/usuarios/**").permitAll()
                                                .requestMatchers("/api/mascotas/**")
                                                .hasAnyRole("DUEÑO", "ASISTENTE", "VETERINARIO")
                                                .requestMatchers("/api/citas/**")
                                                .hasAnyRole("DUEÑO", "ASISTENTE", "VETERINARIO")
                                                .requestMatchers("/api/historias/**").hasAnyRole("VETERINARIO")
                                                .requestMatchers("/api/vacunas/**").hasAnyRole("VETERINARIO")

                                                // Cualquier otra ruta requiere autenticación
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList(
                                "http://localhost:3000",
                                "http://127.0.0.1:5500",
                                "http://localhost:8080",
                                "http://127.0.0.1:8080"));
                configuration.setAllowedMethods(Arrays.asList(
                                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
                configuration.setAllowedHeaders(Arrays.asList(
                                "Authorization",
                                "Content-Type",
                                "Cache-Control",
                                "X-Requested-With",
                                "Accept",
                                "Origin"));
                configuration.setExposedHeaders(Arrays.asList("Authorization"));
                configuration.setAllowCredentials(true);
                configuration.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }
}