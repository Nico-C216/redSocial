/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.repository;

import edu.avanzada.udistrital.redSocial.modelo.Publicacion;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, UUID> {

    // Obtiene publicaciones de un usuario específico
    List<Publicacion> findByIdUsuario(UUID idUsuario);

}
