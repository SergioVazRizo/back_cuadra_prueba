package com.cuadra.cuadra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.cuadra.cuadra.model.SaludDTO;
import com.cuadra.cuadra.model.Usuario;
import com.cuadra.cuadra.repository.SaludRepository;
import com.cuadra.cuadra.service.UsuarioService;

@Service
public class SaludService {

    @Autowired
    private SaludRepository saludRepository;

    @Lazy
    @Autowired
    private UsuarioService usuarioService;

    public SaludDTO obtenerInformacionSaludPorIdUsuario(Long idUsuario) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
        return saludRepository.findByUsuarioId(usuario);
    }
    
    public double calcularIMC(double pesoKg, double alturaCm) {
        double alturaM = alturaCm / 100;
        return pesoKg / (alturaM * alturaM);
    }

    public double calcularPesoIdeal(String sexo, double alturaCm) {
        if (sexo.equalsIgnoreCase("Masculino")) {
            return (alturaCm - 100) * 0.9; 
        } else if (sexo.equalsIgnoreCase("Femenino")) {
            return (alturaCm - 100) * 0.85;
        } else {
            // Manejo para sexo no especificado
            return -1; 
        }
    }

    // Método para calcular la presión arterial ideal (simplificado)
    public String calcularPresionArterialIdeal(int edad) {
        if (edad < 18) {
            return "Consultar con un médico";
        } else if (edad <= 35) {
            return "120/80 mmHg"; 
        } else if (edad <= 55) {
            return "125/85 mmHg"; 
        } else {
            return "130/90 mmHg"; 
        }
    }
}
