package com.cuadra.cuadra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuadra.cuadra.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    
} 
