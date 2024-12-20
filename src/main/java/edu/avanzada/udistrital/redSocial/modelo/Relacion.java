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
 * Representa una relación entre usuarios (seguidores y seguidos)
 *
 * @author Nicolas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relacion {

    private UUID id = UUID.randomUUID();
    private UUID idSeguidor;
    private UUID idSeguido;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

}
