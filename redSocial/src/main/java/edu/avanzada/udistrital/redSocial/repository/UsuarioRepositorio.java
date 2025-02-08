/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.repository;

import edu.avanzada.udistrital.redSocial.modelo.Usuario;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar la entidad Usuario
 *
 * @author Nicolas
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

    /**
     * Buscar un usuario por su nombre de usuario
     * @param username Nombre de usuario
     * @return Usuario encontrado
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Buscar un usuario por su email
     * @param email Correo del usuario
     * @return Usuario encontrado
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verificar si existe un usuario con un email específico
     * @param email Correo del usuario
     * @return true si existe, false si no
     */
    boolean existsByEmail(String email);

}
