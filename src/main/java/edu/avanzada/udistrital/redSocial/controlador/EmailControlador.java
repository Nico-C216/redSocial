/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.controlador;

import edu.avanzada.udistrital.redSocial.domain.EmailDTO;
import edu.avanzada.udistrital.redSocial.service.IEmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author asfak
 */
@RestController

public class EmailControlador {
    
    @Autowired 
    private IEmailServicio iemailServicio;
    
    
    @PostMapping("/EnviarEmail")
    public ResponseEntity<?> recieveRequestEmail(@RequestBody EmailDTO emailDTO){ //deserializacion de JSON 
        
        System.out.println("Mensaje recibido" + emailDTO);
        
        iemailServicio.sendEmail(emailDTO.getToUser(), emailDTO.getSubject() ,emailDTO.getMessage());
        
        return ResponseEntity.ok().build();
        
    }
    
}
