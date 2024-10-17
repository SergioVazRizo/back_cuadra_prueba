package com.cuadra.cuadra.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SaludService saludService; 

    public Usuario obtenerPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    // 1. Registrar Usuario
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.getDispositivo().setClaveUnica(UUID.randomUUID().toString());
        usuario.getDispositivo().setActivo(true); // Establecer el dispositivo como activo por defecto
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return nuevoUsuario;
    }

    // 2. Autenticar Usuario (con validación de clave única y actualización de último acceso)
    @Transactional
    public Usuario autenticarUsuario(String nombreUsuario, String contrasena, String claveUnica) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombreUsuario(nombreUsuario);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Dispositivo dispositivo = usuario.getDispositivo();

            if (contrasena.equals(usuario.getContrasena()) && 
                claveUnica.equals(dispositivo.getClaveUnica()) &&
                dispositivo.isActivo()) { 

                // Actualizar último acceso y tiempo activo
                LocalDateTime ahora = LocalDateTime.now();
                dispositivo.setUltimoAcceso(ahora);

                dispositivoRepository.save(dispositivo);
                return usuario;
            } else {
                throw new Exception("Credenciales incorrectas o dispositivo no activo."); 
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
    @Transactional
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
    @Transactional
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
    @Transactional
    public void activarUsuario(Long usuarioId) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
            usuario.getDispositivo().setActivo(true);
            usuarioRepository.save(usuario);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }
    }

    // 8. Obtener información de salud del usuario
    public com.cuadra.cuadra.model.SaludDTO obtenerInformacionSalud(Long usuarioId) {
        Usuario usuario = obtenerUsuarioPorId(usuarioId);

        double imc = saludService.calcularIMC(usuario.getPesoActual(), usuario.getAltura());
        double pesoIdeal = saludService.calcularPesoIdeal(usuario.getSexo(), usuario.getAltura());
        String presionArterialIdeal = saludService.calcularPresionArterialIdeal(usuario.getEdad());

        com.cuadra.cuadra.model.SaludDTO saludDTO = new com.cuadra.cuadra.model.SaludDTO();
        saludDTO.setImc(imc);
        saludDTO.setPesoIdeal(pesoIdeal);
        saludDTO.setPresionArterialIdeal(presionArterialIdeal);

        return saludDTO;
    }

    public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get();
        } else {
            throw new Exception("Usuario no encontrado."); 
        }
    }
}