/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
document.getElementById("registroForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!username || !email || !password) {
        alert("Por favor, completa todos los campos.");
        return;
    }

    const usuario = {
        username: username,
        email: email,
        password: password,
    };

    fetch("http://localhost:8080/usuarios", { // Cambia a tu backend real si el puerto es diferente
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(usuario),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Error al registrar usuario. Verifica los datos o el servidor.");
            }
            return response.json();
        })
        .then((data) => {
            alert(`¡Usuario registrado con éxito, ${data.username}!`);
            window.location.href = "pagina-principal.html"; // Redirige a la página principal
        })
        .catch((error) => {
            console.error("Error:", error);
            alert("Ocurrió un error durante el registro. Intenta nuevamente.");
        });
});

