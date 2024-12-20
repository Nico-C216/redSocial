/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import edu.avanzada.udistrital.redSocial.modelo.MeGusta;
import edu.avanzada.udistrital.redSocial.repository.MeGustaRepositorio;
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
 * Controlador para gestionar los me gusta en la red social
 * @author Nicolas
 */
@RestController
@RequestMapping("/me-gusta")
@RequiredArgsConstructor
public class MeGustaControlador {
    
    private final MeGustaRepositorio meGustaRepositorio;
    
    @GetMapping("/publicacion/{idPublicacion}")
    public List<MeGusta> obtenerMeGustasPorPublicacion(@PathVariable("idPublicacion") UUID idPublicacion){
        return meGustaRepositorio.findByIdPublicacion(idPublicacion);
    }
    
    @PostMapping
    public ResponseEntity<MeGusta> crearmegusta(@RequestBody MeGusta meGusta){
        meGusta.setFechaCreacion(LocalDateTime.now());
        MeGusta nuevoMeGusta = meGustaRepositorio.save(meGusta);
        return ResponseEntity.ok(nuevoMeGusta);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMeGusta(@PathVariable("id") UUID id){
        if(meGustaRepositorio.existsById(id)){
            meGustaRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
}
