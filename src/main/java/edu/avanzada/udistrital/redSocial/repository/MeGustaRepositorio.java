/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.repository;

import edu.avanzada.udistrital.redSocial.modelo.MeGusta;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar la entidad MeGusta
 *
 * @author Nicolas
 */
@Repository
public interface MeGustaRepositorio extends JpaRepository<MeGusta, UUID> {

    // Obtiene los "Me gusta" de una publicación específica
    List<MeGusta> findByIdPublicacion(UUID idPublicacion);

    // Verifica si un usuario ya dio "Me gusta" a una publicación
    boolean existsByIdPublicacionAndIdUsuario(UUID idPublicacion, UUID idUsuario);

}
