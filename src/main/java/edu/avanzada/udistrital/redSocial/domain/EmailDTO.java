/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author asfak
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmailDTO {
    
    //un DTO es una clase normal que representa un JSON convirtiendolo a un objeto(Data Transfer Objetct)
    
    private String toUser;
    private String subject;
    private String message;
    
    
    
}
