/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import edu.avanzada.udistrital.redSocial.modelo.MeGusta;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Nicolas
 */
public interface IMeGustaServicio {

    List<MeGusta> obtenerPorPublicacion(UUID idPublicacion);

    MeGusta crearMeGusta(MeGusta meGusta);

    void eliminarMeGusta(UUID id);

    boolean existePorId(UUID id);

}
