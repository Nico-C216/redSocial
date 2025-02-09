/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author asfak
 */
@Configuration
public class EmailConfig {
    
    //@Value("${email.sender}")
    //private String emailUser;
    
    //CAMBIAR SETUSERNAME Y SETPASSWORD PARA QUE SE TOMEN DE PROPERTIES
    @Bean
    public JavaMailSender getJavaMailSender(){
    
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("jfwilchesg@gmail.com"); 
        mailSender.setPassword("szop elds lxlw duob");
        
        Properties props= mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls","true"); //protocolo smtp
        props.put("mail.debug","true");
        
        
        return mailSender;
    
        
    
    }
    
}
