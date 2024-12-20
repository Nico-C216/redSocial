/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.avanzada.udistrital.redSocial.modelo;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publicacion {

    private UUID id = UUID.randomUUID();
    private UUID idUsuario;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private int cantidadMeGusta = 0;

}
