/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import edu.avanzada.udistrital.redSocial.modelo.Publicacion;
import edu.avanzada.udistrital.redSocial.repository.PublicacionRepositorio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
@RequiredArgsConstructor
public class PublicacionServicio implements IPublicacionServicio {

    private final PublicacionRepositorio publicacionRepositorio;

    public List<Publicacion> obtenerTodas() {
        return publicacionRepositorio.findAll();
    }

    public Optional<Publicacion> obtenerPorId(UUID id) {
        return publicacionRepositorio.findById(id);
    }

    public Publicacion crearPublicacion(Publicacion publicacion) {
        publicacion.setFechaCreacion(LocalDateTime.now());
        return publicacionRepositorio.save(publicacion);
    }

    public void eliminarPublicacion(UUID id) {
        publicacionRepositorio.deleteById(id);
    }

    public boolean existePorId(UUID id) {
        return publicacionRepositorio.existsById(id);
    }

}
