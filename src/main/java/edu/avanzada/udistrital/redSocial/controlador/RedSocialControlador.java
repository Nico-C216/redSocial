/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Nicolas
 */
@Controller
public class RedSocialControlador {

    // Ruta principal redirige a registro si no hay sesión
    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/registro.html";
        }
        return "redirect:/red-social.html";
    }

    // Página de registro
    @GetMapping("/registro")
    public String registro(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/red-social.html";
        }
        return "registro.html";
    }

    // Página principal de la red social
    @GetMapping("/red-social")
    public String redSocial(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/registro.html";
        }
        return "red_social.html";
    }

    // Página de perfil
    @GetMapping("/perfil")
    public String perfil(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/registro.html";
        }
        return "perfil.html";
    }

    // Página de perfil de otro usuario
    @GetMapping("/perfil/{username}")
    public String perfilUsuario(@PathVariable String username, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/registro.html";
        }
        return "perfil.html";
    }

}
