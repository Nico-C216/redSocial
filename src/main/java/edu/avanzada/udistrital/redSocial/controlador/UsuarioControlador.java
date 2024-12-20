/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import edu.avanzada.udistrital.redSocial.modelo.Usuario;
import edu.avanzada.udistrital.redSocial.repository.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gestionar usuarios en la red social
 *
 * @author Nicolas
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    
    private final UsuarioRepositorio usuarioRepositorio;

    /**
     * Obtiene todos los usuarios
     * @return 
     */
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    /**
     * Obtiene al usuario por el id
     * @param id
     * @return 
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") UUID id) {
        return usuarioRepositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea al usuario en la red social
     * @param usuario
     * @return 
     */
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepositorio.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        Usuario nuevoUsuario = usuarioRepositorio.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    /**
     * Actualiza al usuario
     * @param id
     * @param usuario
     * @return 
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id") UUID id, @RequestBody Usuario usuario) {
        if (!usuarioRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuario.setId(id);
        Usuario usuarioActualizado = usuarioRepositorio.save(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    /**
     * Elimina el usuario
     * @param id
     * @return 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") UUID id) {
        if (usuarioRepositorio.existsById(id)) {
            usuarioRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
