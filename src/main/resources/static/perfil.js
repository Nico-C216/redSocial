/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
/* perfil.js */
document.addEventListener('DOMContentLoaded', async () => {
    try {
        // Se intenta obtener el usuario de la sesión
        const response = await fetch('http://localhost:8090/usuarios/session', {
            credentials: 'include'
        });
        if (!response.ok)
            throw new Error('Usuario no autenticado');
        const user = await response.json();

        // Rellenar campos del perfil
        document.getElementById('user-name').textContent = user.username;
        document.getElementById('user-email').textContent = user.email;
        document.getElementById('user-bio').textContent = user.bio || 'Sin biografía';

        // Imagen de perfil
        const profilePicElement = document.getElementById('profile-pic');
        if (user.urlImagenPerfil && user.urlImagenPerfil.trim() !== '') {
            profilePicElement.src = user.urlImagenPerfil;
        } else {
            // Si el usuario no tiene foto, se asigna la imagen por defecto
            profilePicElement.src = "https://www.w3schools.com/howto/img_avatar.png";
        }

        const passwordElement = document.getElementById('user-password');
        passwordElement.dataset.realPassword = user.password; // Guardamos la contraseña real

        passwordElement.addEventListener('mouseenter', () => {
            passwordElement.textContent = passwordElement.dataset.realPassword;
        });

        passwordElement.addEventListener('mouseleave', () => {
            passwordElement.textContent = '******';
        });
    } catch (error) {
        // Si no está autenticado o hay error, redirige a login
        window.location.href = 'login.html';
    }

    // Evento para el botón "Volver"
    document.getElementById('backBtn').addEventListener('click', () => {
        window.location.href = 'red_social.html';
    });
});
