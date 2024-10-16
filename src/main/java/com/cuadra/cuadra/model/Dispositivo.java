package com.cuadra.cuadra.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Dispositivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String imei;

    @Column(unique = true, nullable = false)
    private String claveUnica;

    private LocalDateTime ultimoAcceso;
    private int tiempoActivo; // En segundos
    private boolean activo;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getClaveUnica() {
        return claveUnica;
    }

    public void setClaveUnica(String claveUnica) {
        this.claveUnica = claveUnica;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public int getTiempoActivo() {
        return tiempoActivo;
    }

    public void setTiempoActivo(int tiempoActivo) {
        this.tiempoActivo = tiempoActivo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
