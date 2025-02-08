/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.repository;

import edu.avanzada.udistrital.redSocial.modelo.Relacion;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public interface RelacionRepositorio extends JpaRepository<Relacion, UUID> {

    // Obtiene los usuarios seguidos por un usuario
    List<Relacion> findByIdSeguidor(UUID idSeguidor);

    // Obtiene los usuarios que siguen a un usuario
    List<Relacion> findByIdSeguido(UUID idSeguido);
}
