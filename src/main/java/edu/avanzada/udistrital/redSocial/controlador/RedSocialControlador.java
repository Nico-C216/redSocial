/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Nicolas
 */
@Controller
public class RedSocialControlador {

    @GetMapping("/registro")
    public String mostrarRegistro() {
        // Muestra la vista de registro (registro.html)
        return "registro";
    }


    @GetMapping("/pagina-principal")
    public String mostrarPaginaPrincipal() {
        // Muestra la vista de la página principal (pagina-principal.html)
        return "pagina-principal";
    }

}
