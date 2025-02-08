/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.repository;

import edu.avanzada.udistrital.redSocial.modelo.Comentario;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar la entidad Comentario
 *
 * @author Nicolas
 */
@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, UUID> {

    // Obtiene los comentarios de una publicación específica
    List<Comentario> findByIdPublicacion(UUID idPublicacion);

}
