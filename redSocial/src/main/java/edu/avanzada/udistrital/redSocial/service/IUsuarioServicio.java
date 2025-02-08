/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import edu.avanzada.udistrital.redSocial.modelo.Usuario;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Nicolas
 */
public interface IUsuarioServicio {

    List<Usuario> obtenerTodos();

    Optional<Usuario> obtenerPorId(UUID id);

    boolean existePorEmail(String email);

    Usuario crearUsuario(Usuario usuario);

    Usuario actualizarUsuario(UUID id, Usuario usuario);

    void eliminarUsuario(UUID id);

}
