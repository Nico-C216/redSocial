/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import edu.avanzada.udistrital.redSocial.modelo.Comentario;
import edu.avanzada.udistrital.redSocial.modelo.Usuario;
import edu.avanzada.udistrital.redSocial.repository.ComentarioRepositorio;
import edu.avanzada.udistrital.redSocial.service.ComentarioServicio;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gestionar los comentarios en la red social
 *
 * @author Nicolas
 */
@RestController
@RequestMapping("/comentarios")
@RequiredArgsConstructor
public class ComentarioControlador {

    private final ComentarioServicio comentarioServicio;

    /**
     * Obtiene todos los comentarios
     *
     * @return
     */
    @GetMapping
    public List<Comentario> obtenerTodosLosComentarios() {
        return comentarioServicio.obtenerTodos();
    }

    /**
     * Obtiene los comentarios de las publicaciones
     *
     * @param idPublicacion
     * @return
     */
    @GetMapping("/publicacion/{idPublicacion}")
    public List<Comentario> obtenerComentariosPorPublicacion(@PathVariable("idPublicacion") UUID idPublicacion) {
        return comentarioServicio.obtenerPorPublicacion(idPublicacion);
    }

    /**
     * Crea los comentarios
     *
     * @param comentario
     * @return
     */
    @PostMapping
    public ResponseEntity<Comentario> crearComentario(@RequestBody Comentario comentario, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        comentario.setIdUsuario(usuario.getId());
        Comentario nuevoComentario = comentarioServicio.crearComentario(comentario);
        return ResponseEntity.ok(nuevoComentario);
    }

    /**
     * Elimina los comentarios
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable("id") UUID id) {
        if (!comentarioServicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        comentarioServicio.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }

}
