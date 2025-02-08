/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import edu.avanzada.udistrital.redSocial.modelo.Comentario;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Nicolas
 */
public interface IComentarioServicio {

    List<Comentario> obtenerTodos();

    List<Comentario> obtenerPorPublicacion(UUID idPublicacion);

    Comentario crearComentario(Comentario comentario);

    void eliminarComentario(UUID id);

    boolean existePorId(UUID id);

}
