package com.cuadra.cuadra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cuadra.cuadra.model.SaludDTO;
import com.cuadra.cuadra.model.Usuario;

public interface SaludRepository extends JpaRepository<SaludDTO, Long> {
    SaludDTO findByUsuarioId(Usuario usuario);
}
