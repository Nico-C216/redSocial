/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Usuario
 * Created: 24/12/2024
 */
-- Crear usuarios de prueba
INSERT INTO usuario (id, username, email, password, bio, url_imagen_perfil, creacion, actualizacion) VALUES 
(UUID(), 'usuario1', 'usuario1@example.com', 'password1', 'Bio de usuario 1', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(UUID(), 'usuario2', 'usuario2@example.com', 'password2', 'Bio de usuario 2', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Crear publicaciones de prueba
INSERT INTO publicacion (id, id_usuario, fecha_creacion, cantidad_me_gusta) VALUES 
(UUID(), (SELECT id FROM usuario WHERE username = 'usuario1'), CURRENT_TIMESTAMP, 5),
(UUID(), (SELECT id FROM usuario WHERE username = 'usuario2'), CURRENT_TIMESTAMP, 10);
