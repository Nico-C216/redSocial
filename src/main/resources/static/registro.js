/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
// registro.js
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registroForm');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const userData = {
            username: formData.get('username'),
            email: formData.get('email'),
            password: formData.get('password'),
            bio: formData.get('bio')
        };

        try {
            const response = await fetch('http://localhost:8090/usuarios/registrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
                        credentials: 'include',
            });
            localStorage.setItem('user', JSON.stringify(userData));
            window.location.href = '/red-social';

            if (response.redirected) {
                window.location.href = response.url; // Redirige automáticamente
            } else if (!response.ok) {
                throw new Error('Error en el registro');
            }

            // Obtener el usuario recién creado para guardarlo en localStorage
            const userResponse = await fetch(`http://localhost:8090/usuarios?email=${formData.email}`);
            const userData = await userResponse.json();

            // Guardar datos del usuario en localStorage
            localStorage.setItem('user', JSON.stringify(userData));

            // Mostrar mensaje de éxito
            showMessage('success', '¡Registro exitoso! Redirigiendo...');

            // Redireccionar a la red social después de 2 segundos
            setTimeout(() => {
                window.location.href = '/red-social';
            }, 2000);

        } catch (error) {
            showMessage('error', 'Error en el registro. Por favor, intenta nuevamente.');
        }
    });


    // Función para mostrar mensajes
    function showMessage(type, text) {
        const existingMessage = document.querySelector('.message');
        if (existingMessage) {
            existingMessage.remove();
        }

        const message = document.createElement('div');
        message.className = `message ${type}-message fade-in fixed top-4 right-4 p-4 rounded-lg shadow-lg`;
        message.textContent = text;
        document.body.appendChild(message);

        setTimeout(() => {
            message.remove();
        }, 3000);
    }
});

