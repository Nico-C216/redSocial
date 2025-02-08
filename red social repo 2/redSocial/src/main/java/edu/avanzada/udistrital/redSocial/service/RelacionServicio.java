/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import edu.avanzada.udistrital.redSocial.modelo.Relacion;
import edu.avanzada.udistrital.redSocial.repository.RelacionRepositorio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
@RequiredArgsConstructor
public class RelacionServicio implements IRelacionServicio {
    
    private final RelacionRepositorio relacionRepositorio;

    public List<Relacion> obtenerTodas() {
        return relacionRepositorio.findAll();
    }

    public List<Relacion> obtenerPorSeguidor(UUID idSeguidor) {
        return relacionRepositorio.findByIdSeguidor(idSeguidor);
    }

    public Relacion crearRelacion(Relacion relacion) {
        relacion.setFechaCreacion(LocalDateTime.now());
        return relacionRepositorio.save(relacion);
    }

    public void eliminarRelacion(UUID id) {
        relacionRepositorio.deleteById(id);
    }

    public boolean existePorId(UUID id) {
        return relacionRepositorio.existsById(id);
    }
    
}
