package com.cuadra.cuadra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class SaludDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double imc;
    private double pesoIdeal;
    private String nombre;
    private String descripcion;
    private String presionArterialIdeal;

    @ManyToOne // Indica que un registro de salud está relacionado con un usuario
    private Usuario usuario; // Asegúrate de que la clase Usuario esté definida

    // Getters y setters 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public double getPesoIdeal() {
        return pesoIdeal;
    }

    public void setPesoIdeal(double pesoIdeal) {
        this.pesoIdeal = pesoIdeal;
    }

    public String getPresionArterialIdeal() {
        return presionArterialIdeal;
    }

    public void setPresionArterialIdeal(String presionArterialIdeal) {
        this.presionArterialIdeal = presionArterialIdeal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
