package com.cuadra.cuadra.model;

public class SaludDTO {
    private double imc;
    private double pesoIdeal;
    private String presionArterialIdeal;

    // Getters y setters 
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
}

