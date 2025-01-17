/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import edu.avanzada.udistrital.redSocial.modelo.Publicacion;
import edu.avanzada.udistrital.redSocial.repository.PublicacionRepositorio;
import edu.avanzada.udistrital.redSocial.service.PublicacionServicio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
 * @author Nicolas
 */
@RestController
@RequestMapping("/publicaciones")
@RequiredArgsConstructor
public class PublicacionControlador {
    
    
    private final PublicacionServicio publicacionServicio;
    
    /**
     * Obtiene todas las publicaciones
     * @return 
     */
    @GetMapping
    public List<Publicacion> obtenerTodasLasPublicaciones() {
        return publicacionServicio.obtenerTodas();
    }
    
    /**
     * Obtiene las publicaciones por id
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
     * @param publicacion
     * @return 
     */
    @PostMapping
     public ResponseEntity<Publicacion> crearPublicacion(@RequestBody Publicacion publicacion) {
        Publicacion nuevaPublicacion = publicacionServicio.crearPublicacion(publicacion);
        return ResponseEntity.ok(nuevaPublicacion);
    }
    
    /**
     * Elimina las publicaciones
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
