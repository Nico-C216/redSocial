/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import edu.avanzada.udistrital.redSocial.modelo.Relacion;
import edu.avanzada.udistrital.redSocial.repository.RelacionRepositorio;
import edu.avanzada.udistrital.redSocial.service.RelacionServicio;
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
 * Controlador para gestionar las relaciones en la red social
 * @author Nicolas
 */
@RestController
@RequestMapping("/relaciones")
@RequiredArgsConstructor
public class RelacionControlador {
    
    
    private final RelacionServicio relacionServicio;
    
    @GetMapping
    public List<Relacion> obtenerTodasLasRelaciones() {
        return relacionServicio.obtenerTodas();
    }
    
    @GetMapping("/seguidor/{idSeguidor}")
    public List<Relacion> obtenerRelacionesPorSeguidor(@PathVariable("idSeguidor") UUID idSeguidor) {
        return relacionServicio.obtenerPorSeguidor(idSeguidor);
    }
    
    @PostMapping
    public ResponseEntity<Relacion> crearRelacion(@RequestBody Relacion relacion) {
        Relacion nuevaRelacion = relacionServicio.crearRelacion(relacion);
        return ResponseEntity.ok(nuevaRelacion);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRelacion(@PathVariable("id") UUID id) {
        if (!relacionServicio.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        relacionServicio.eliminarRelacion(id);
        return ResponseEntity.noContent().build();
    }
    
}
