/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import edu.avanzada.udistrital.redSocial.modelo.Comentario;
import edu.avanzada.udistrital.redSocial.modelo.Publicacion;
import edu.avanzada.udistrital.redSocial.modelo.Usuario;
import edu.avanzada.udistrital.redSocial.repository.PublicacionRepositorio;
import edu.avanzada.udistrital.redSocial.service.ComentarioServicio;
import edu.avanzada.udistrital.redSocial.service.MeGustaServicio;
import edu.avanzada.udistrital.redSocial.service.PublicacionServicio;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
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
 * Controlador para gestionar los post o publicaciones en la red social
 *
 * @author Nicolas
 */
@RestController
@RequestMapping("/publicaciones")
@RequiredArgsConstructor
public class PublicacionControlador {

    private final PublicacionServicio publicacionServicio;
    private final ComentarioServicio comentarioServicio;
    private final MeGustaServicio meGustaServicio;

    /**
     * Obtiene todas las publicaciones
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> obtenerPublicaciones() {
        List<Publicacion> publicaciones = publicacionServicio.obtenerTodas();

        List<Map<String, Object>> publicacionesEnriquecidas = publicaciones.stream().map(pub -> {
            Map<String, Object> publicacionData = new HashMap<>();
            publicacionData.put("id", pub.getId());
            publicacionData.put("contenido", pub.getContenido());
            publicacionData.put("fechaCreacion", pub.getFechaCreacion());

            // Obtener y actualizar la cantidad de "me gusta"
            int cantidadMeGusta = meGustaServicio.obtenerPorPublicacion(pub.getId()).size();
            publicacionData.put("cantidadMeGusta", cantidadMeGusta);

            // Obtener y agregar comentarios
            List<Comentario> comentarios = comentarioServicio.obtenerPorPublicacion(pub.getId());
            System.out.println("[DEBUG] Comentarios de Publicación " + pub.getId() + ": " + comentarios);

            publicacionData.put("comentarios", comentarios);
            return publicacionData;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(publicacionesEnriquecidas);
    }

    /**
     * Obtiene las publicaciones por id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Publicacion> obtenerPublicacionPorId(@PathVariable("id") UUID id) {
        return publicacionServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea las publicaciones
     *
     * @param publicacion
     * @param session
     * @return
     */
    @PostMapping
    public ResponseEntity<?> crearPublicacion(@RequestBody Publicacion publicacion, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no autenticado.");
        }

        // Asignar el usuario autenticado a la publicación
        publicacion.setIdUsuario(usuario.getId());

        // Validar contenido
        if (publicacion.getContenido() == null || publicacion.getContenido().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El contenido de la publicación no puede estar vacío.");
        }

        // Guardar publicación
        Publicacion nuevaPublicacion = publicacionServicio.crearPublicacion(publicacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPublicacion);
    }

    /**
     * Elimina las publicaciones
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable("id") UUID id) {
        if (!publicacionServicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        publicacionServicio.eliminarPublicacion(id);
        return ResponseEntity.noContent().build();
    }

}
