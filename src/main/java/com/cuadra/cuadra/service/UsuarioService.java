package com.cuadra.cuadra.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cuadra.cuadra.model.Dispositivo;
import com.cuadra.cuadra.model.Usuario;
import com.cuadra.cuadra.repository.DispositivoRepository;
import com.cuadra.cuadra.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    // 1. Registrar Usuario
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.getDispositivo().setClaveUnica(UUID.randomUUID().toString());
        return usuarioRepository.save(usuario);
    }

    // 2. Autenticar Usuario (para login)
    public Usuario autenticarUsuario(String nombreUsuario, String contrasena) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombreUsuario(nombreUsuario);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (contrasena.equals(usuario.getContrasena())) {
                Dispositivo dispositivo = usuario.getDispositivo();
                dispositivo.setUltimoAcceso(LocalDateTime.now());
                dispositivoRepository.save(dispositivo);
                return usuario;
            } else {
                throw new Exception("Contraseña incorrecta."); 
            }
        } else {
            throw new Exception("Usuario no encontrado."); 
        }
    }

    // 3. Obtener todos los usuarios (para administradores)
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // 4. Obtener usuario por ID
    public Usuario obtenerUsuarioPorId(Long usuarioId) {
        try {
            return usuarioRepository.findById(usuarioId).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }
    }

    // 5. Actualizar usuario
    public Usuario actualizarUsuario(Long usuarioId, Usuario usuarioActualizado) {
        try {
            Usuario usuarioExistente = usuarioRepository.findById(usuarioId).orElseThrow();
            usuarioExistente.setNombreCompleto(usuarioActualizado.getNombreCompleto());
            // ... Actualizar otros campos si es necesario
            return usuarioRepository.save(usuarioExistente);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }
    }

    // 6. Desactivar usuario
    public void desactivarUsuario(Long usuarioId) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
            usuario.getDispositivo().setActivo(false);
            usuarioRepository.save(usuario);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }
    }

    // 7. Activar usuario
    public void activarUsuario(Long usuarioId) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
            usuario.getDispositivo().setActivo(true);
            usuarioRepository.save(usuario);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }
    }
}