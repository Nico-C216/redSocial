/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.service;

import java.io.File;

/**
 *
 * @author asfak
 */
public interface IEmailServicio {
    
    void sendEmail(String toUser,String subject, String message);
    
    void sendEmailWithFile(String toUser,String subject, String message, File file);
    
    
}
