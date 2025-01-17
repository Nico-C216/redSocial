/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Nicolas
 */
@Controller
public class RedSocialControlador {
    
    @GetMapping("/registro")
    public String registro(){
        return "registro";
    }

    @GetMapping("/red_social")
    public String redSocial(){
        return "red_social";
    }

}
