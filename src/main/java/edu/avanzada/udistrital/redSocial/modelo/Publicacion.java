/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una publicación en la red social (similar a un tweet o post)
 *
 * @author Nicolas
 */
@Entity
@Data
@NoArgsConstructor
@Table(name="publicaciones")
public class Publicacion {

    @Id
    private UUID id = UUID.randomUUID();
    @JsonIgnore
    private UUID idUsuario;
    private String contenido;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private int cantidadMeGusta = 0;
    
    public Publicacion(UUID id){
        this.id=id;
    }

}
