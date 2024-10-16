package com.cuadra.cuadra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuadra.cuadra.model.Dispositivo;
import java.util.Optional;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
    Optional<Dispositivo> findByClaveUnica(String claveUnica);
}
