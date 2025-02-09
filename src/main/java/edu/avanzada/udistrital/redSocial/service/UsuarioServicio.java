/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.avanzada.udistrital.redSocial.modelo.Usuario;
import edu.avanzada.udistrital.redSocial.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Usuario
 */
@Service
@RequiredArgsConstructor
public class UsuarioServicio implements IUsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;

    public List<Usuario> obtenerTodos() {
        return usuarioRepositorio.findAll();
    }

    public Optional<Usuario> obtenerPorId(UUID id) {
        return usuarioRepositorio.findById(id);
    }

    public boolean existePorEmail(String email) {
        return usuarioRepositorio.existsByEmail(email);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuarioRepositorio.existsByEmail(usuario.getEmail())) {
            return false;
        }

        usuario.setUsername(usuario.getUsername());
        usuario.setEmail(usuario.getEmail());
        usuario.setPassword(usuario.getPassword());
        usuarioRepositorio.save(usuario);
        return true;
    }

    public Usuario actualizarUsuario(UUID id, Usuario usuario) {
        usuario.setId(id);
        return usuarioRepositorio.save(usuario);
    }

    public void eliminarUsuario(UUID id) {
        usuarioRepositorio.deleteById(id);
    }

    public Optional<Usuario> validarCredenciales(String username, String password) { //Optional maneja valores presentes o ausentes
        return usuarioRepositorio.findByUsername(username).filter(usuario -> usuario.getPassword().equals(password)); //verifica la contraseña en la base de datos sin seguiridad
    } //envia Optional(usuario), si existe optional contiene el usuario si no es Optional.empty
    
    public Usuario autenticarUsuario(String username, String password) {
        return usuarioRepositorio.findByUsername(username).filter(usuario -> usuario.getPassword().equals(password)).orElse(null);
    }
}
