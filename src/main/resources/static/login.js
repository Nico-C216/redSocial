/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = {
            username: document.getElementById('username').value,
            password: document.getElementById('password').value,
        };
        //depuracion
        console.log("Datos enviados:", JSON.stringify(formData));
        
        try {
            const response = await fetch('http://localhost:8090/usuarios/login2', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
                credentials: 'include', // Enviar cookies para la sesión
            });

            const result = await response.json();
            //depuracion
            console.log("Estado HTTP:", response.status);
            console.log("Respuesta del servidor:", result);
            
            if (!response.ok) {
                throw new Error('Credenciales incorrectas error 1');
            }
            if (result.redirectUrl) {
                //depuracion
                console.log("Redirigiendo a:", result.redirectUrl);
                
                window.location.href = result.redirectUrl;
            }
            // El navegador seguirá automáticamente la redirección del backend
        } catch (error) {
            showMessage('error', 'Credenciales incorrectas.');
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

