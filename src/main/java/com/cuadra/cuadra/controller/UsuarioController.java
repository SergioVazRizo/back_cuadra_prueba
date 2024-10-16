package com.cuadra.cuadra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.cuadra.cuadra.model.SaludDTO;
import com.cuadra.cuadra.model.Usuario;
import com.cuadra.cuadra.service.SaludService;
import com.cuadra.cuadra.service.UsuarioService;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8080")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SaludService saludService;

    // Endpoint para obtener el perfil del usuario autenticado
    @GetMapping("/perfil") 
    public ResponseEntity<Usuario> obtenerPerfilUsuario() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getUsername(); 

        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorNombreUsuario(nombreUsuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para obtener la información de salud del usuario autenticado
    @GetMapping("/salud")
    public ResponseEntity<SaludDTO> obtenerInformacionSalud() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getUsername(); 

        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorNombreUsuario(nombreUsuario);
            SaludDTO saludDTO = usuarioService.obtenerInformacionSalud(usuario.getId());
            return new ResponseEntity<>(saludDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 1. Registrar Usuario
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody @Valid Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 2. Autenticar Usuario (modificado para recibir clave única)
    @PostMapping("/login")
    public ResponseEntity<Usuario> autenticarUsuario(
            @RequestParam String nombreUsuario, 
            @RequestParam String contrasena,
            @RequestParam String claveUnica) { 

        try {
            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(nombreUsuario, contrasena, claveUnica);
            return new ResponseEntity<>(usuarioAutenticado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // 3. Obtener todos los usuarios (solo para administradores - requiere autenticación y autorización)
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 4. Obtener usuario por ID (requiere autenticación y autorización)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. Actualizar usuario (requiere autenticación y autorización)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid Usuario usuarioActualizado) {
        try {
            Usuario usuario = usuarioService.actualizarUsuario(id, usuarioActualizado);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 6. Desactivar usuario (solo para administradores - requiere autenticación y autorización)
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Long id) {
        try {
            usuarioService.desactivarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 7. Activar usuario (solo para administradores - requiere autenticación y autorización)
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarUsuario(@PathVariable Long id) {
        try {
            usuarioService.activarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 8. Calcular IMC (requiere autenticación y autorización)
    @GetMapping("/{id}/salud")
    public ResponseEntity<Double> calcularIMC(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            double imc = saludService.calcularIMC(usuario.getPesoActual(), usuario.getAltura());
            return new ResponseEntity<>(imc, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
