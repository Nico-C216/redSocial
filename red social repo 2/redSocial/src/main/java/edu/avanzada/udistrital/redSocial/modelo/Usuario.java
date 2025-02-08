/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un usuario en la red social
 *
 * @author Nicolas
 */
@Entity
@Data
@NoArgsConstructor
@Table(name="usuarios")
public class Usuario {

    @Id
    private UUID id = UUID.randomUUID();
    private String username;
    private String email;
    private String password;
    private String bio;
    private String urlImagenPerfil;
    private LocalDateTime creacion = LocalDateTime.now();
    private LocalDateTime actualizacion = LocalDateTime.now();
    
    public Usuario(UUID id){
        this.id=id;
    }

}
