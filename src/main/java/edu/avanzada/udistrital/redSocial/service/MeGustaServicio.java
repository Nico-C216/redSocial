/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import edu.avanzada.udistrital.redSocial.modelo.MeGusta;
import edu.avanzada.udistrital.redSocial.repository.MeGustaRepositorio;
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
public class MeGustaServicio {

    private final MeGustaRepositorio meGustaRepositorio;

    public List<MeGusta> obtenerPorPublicacion(UUID idPublicacion) {
        return meGustaRepositorio.findByIdPublicacion(idPublicacion);
    }

    public MeGusta crearMeGusta(MeGusta meGusta) {
        meGusta.setFechaCreacion(LocalDateTime.now());
        return meGustaRepositorio.save(meGusta);
    }

    public void eliminarMeGusta(UUID id) {
        meGustaRepositorio.deleteById(id);
    }

    public boolean existePorId(UUID id) {
        return meGustaRepositorio.existsById(id);
    }

}
