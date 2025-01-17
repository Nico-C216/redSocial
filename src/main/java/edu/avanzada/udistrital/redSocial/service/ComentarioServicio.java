/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import edu.avanzada.udistrital.redSocial.modelo.Comentario;
import edu.avanzada.udistrital.redSocial.repository.ComentarioRepositorio;
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
public class ComentarioServicio implements IComentarioServicio {

    private final ComentarioRepositorio comentarioRepositorio;

    public List<Comentario> obtenerTodos() {
        return comentarioRepositorio.findAll();
    }

    public List<Comentario> obtenerPorPublicacion(UUID idPublicacion) {
        return comentarioRepositorio.findByIdPublicacion(idPublicacion);
    }

    public Comentario crearComentario(Comentario comentario) {
        comentario.setFechaCreacion(LocalDateTime.now());
        return comentarioRepositorio.save(comentario);
    }

    public void eliminarComentario(UUID id) {
        comentarioRepositorio.deleteById(id);
    }

    public boolean existePorId(UUID id) {
        return comentarioRepositorio.existsById(id);
    }

}
