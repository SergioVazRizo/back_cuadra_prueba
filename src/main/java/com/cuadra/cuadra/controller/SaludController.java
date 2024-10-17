package com.cuadra.cuadra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuadra.cuadra.model.SaludDTO;
import com.cuadra.cuadra.service.SaludService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class SaludController {
 
    @Autowired
    private SaludService saludService;

    @GetMapping("/salud/{idUsuario}")
    public ResponseEntity<SaludDTO> obtenerInformacionSaludPorIdUsuario(@PathVariable Long idUsuario) {
        SaludDTO informacionSalud = saludService.obtenerInformacionSaludPorIdUsuario(idUsuario);
        if (informacionSalud != null) {
            return ResponseEntity.ok(informacionSalud);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
