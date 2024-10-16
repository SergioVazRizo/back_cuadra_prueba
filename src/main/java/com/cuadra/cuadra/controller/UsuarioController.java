package com.cuadra.cuadra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.cuadra.cuadra.model.Usuario;
import com.cuadra.cuadra.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

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

    // 2. Autenticar Usuario (para login)
    @PostMapping("/login")
    public ResponseEntity<Usuario> autenticarUsuario(@RequestParam String nombreUsuario, @RequestParam String contrasena) {
        try {
            Usuario usuarioAutenticado = usuarioService.autenticarUsuario(nombreUsuario, contrasena);
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
}
