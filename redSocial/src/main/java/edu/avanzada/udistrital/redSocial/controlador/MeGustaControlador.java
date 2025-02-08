/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import edu.avanzada.udistrital.redSocial.modelo.MeGusta;
import edu.avanzada.udistrital.redSocial.modelo.Publicacion;
import edu.avanzada.udistrital.redSocial.modelo.Usuario;
import edu.avanzada.udistrital.redSocial.repository.MeGustaRepositorio;
import edu.avanzada.udistrital.redSocial.service.MeGustaServicio;
import edu.avanzada.udistrital.redSocial.service.PublicacionServicio;
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
 * Controlador para gestionar los me gusta en la red social
 *
 * @author Nicolas
 */
@RestController
@RequestMapping("/me-gusta")
@RequiredArgsConstructor
public class MeGustaControlador {

    private final MeGustaServicio meGustaServicio;
    private final PublicacionServicio publicacionServicio;

    @GetMapping("/publicacion/{idPublicacion}")
    public List<MeGusta> obtenerMeGustasPorPublicacion(@PathVariable("idPublicacion") UUID idPublicacion) {
        return meGustaServicio.obtenerPorPublicacion(idPublicacion);
    }

    @PostMapping
    public ResponseEntity<?> crearMeGusta(@RequestBody MeGusta meGusta, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado.");
        }

        // Asignar el ID del usuario autenticado
        meGusta.setIdUsuario(usuario.getId());
        System.out.println("[DEBUG] Me gusta recibido: " + meGusta);

        meGustaServicio.crearMeGusta(meGusta);

        // Retornar publicación actualizada
        Publicacion publicacionActualizada = publicacionServicio.obtenerPorId(meGusta.getIdPublicacion()).orElseThrow();
        publicacionActualizada.setCantidadMeGusta(
                meGustaServicio.obtenerPorPublicacion(meGusta.getIdPublicacion()).size()
        );

        return ResponseEntity.ok(publicacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMeGusta(@PathVariable("id") UUID id) {
        if (!meGustaServicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        meGustaServicio.eliminarMeGusta(id);
        return ResponseEntity.noContent().build();
    }

}
