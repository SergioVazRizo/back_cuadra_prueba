package com.cuadra.cuadra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors() // Habilitar CORS
            .and()
            .csrf().disable() // Deshabilitar CSRF si no es necesario
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/usuarios/login", "/api/usuarios/registrar").permitAll() // Permitir rutas específicas sin autenticación
                .anyRequest().authenticated() // Cualquier otra ruta requiere autenticación
            );
        return http.build();
    }
}
