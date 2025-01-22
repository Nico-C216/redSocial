/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import edu.avanzada.udistrital.redSocial.modelo.Usuario;
import edu.avanzada.udistrital.redSocial.service.UsuarioServicio;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    private final UsuarioServicio usuarioServicio;

    /**
     * Obtiene todos los usuarios
     *
     * @return
     */
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioServicio.obtenerTodos();
    }

    /**
     * Obtiene al usuario por el id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") UUID id) {
        return usuarioServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea al usuario en la red social
     *
     * @param usuario
     * @return
     */
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        if (usuarioServicio.existePorEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        Usuario nuevoUsuario = usuarioServicio.crearUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    /**
     * Registra al usuario en la red social
     *
     *
     * @param usuario
     * @param session
     * @param response
     * @throws java.io.IOException
     */
    @PostMapping("/registrar")
    public void registrarUsuario(@RequestBody Usuario usuario, HttpSession session, HttpServletResponse response) throws IOException {
        boolean registrado = usuarioServicio.registrarUsuario(usuario);
        if (registrado) {
            session.setAttribute("usuario", usuario); // Configurar sesión
            response.sendRedirect("/red-social"); // Redirige directamente
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al registrar usuario");
        }
    }

    /**
     * Actualiza al usuario
     *
     * @param id
     * @param usuario
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable("id") UUID id, @RequestBody Usuario usuario) {
        if (!usuarioServicio.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    /**
     * Elimina el usuario
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") UUID id) {
        if (!usuarioServicio.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuarioServicio.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
