/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import edu.avanzada.udistrital.redSocial.modelo.Relacion;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Nicolas
 */
public interface IRelacionServicio {

    List<Relacion> obtenerTodas();

    List<Relacion> obtenerPorSeguidor(UUID idSeguidor);

    Relacion crearRelacion(Relacion relacion);

    void eliminarRelacion(UUID id);

    boolean existePorId(UUID id);

}
