document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('loginForm');

    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = {
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
        };

        try {
            const response = await fetch('http://localhost:8090/usuarios/login', { 
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
                credentials: 'include', // Enviar cookies para la sesión
            });
            
            const result = await response.json();
            
            if (!response.ok) {
                throw new Error('Credenciales incorrectas');
            }
            if (result.redirectUrl) {
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