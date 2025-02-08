/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import edu.avanzada.udistrital.redSocial.modelo.Publicacion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Nicolas
 */
public interface IPublicacionServicio {

    List<Publicacion> obtenerTodas();

    Optional<Publicacion> obtenerPorId(UUID id);

    Publicacion crearPublicacion(Publicacion publicacion);

    void eliminarPublicacion(UUID id);

    boolean existePorId(UUID id);

}
