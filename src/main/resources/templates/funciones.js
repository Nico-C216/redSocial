/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
// URL base del backend
const apiBaseUrl = 'http://localhost:8090';

// Referencias a las secciones
const seccionRegistro = document.getElementById('seccion-registro');
const seccionPaginaPrincipal = document.getElementById('seccion-pagina-principal');
const seccionPerfil = document.getElementById('seccion-perfil');

// Función para mostrar u ocultar secciones
function mostrarSeccion(seccion) {
    seccionRegistro.classList.remove('visible');
    seccionPaginaPrincipal.classList.remove('visible');
    seccionPerfil.classList.remove('visible');

    switch (seccion) {
        case 'pagina-principal':
            seccionPaginaPrincipal.classList.add('visible');
            cargarPublicaciones();
            break;
        case 'perfil':
            seccionPerfil.classList.add('visible');
            cargarPerfil();
            break;
        default:
            seccionRegistro.classList.add('visible');
    }
}

// Crear usuario
async function crearUsuario() {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const usuario = { username, email, password };

    try {
        const response = await fetch(`${apiBaseUrl}/usuarios`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(usuario)
        });

        if (response.ok) {
            alert('Usuario creado exitosamente.');
            mostrarSeccion('pagina-principal');
        } else {
            alert('Error al crear el usuario. Verifica los datos ingresados.');
        }
    } catch (error) {
        console.error('Error al crear usuario:', error);
        alert('Ocurrió un error al registrar el usuario.');
    }
}

// Cargar publicaciones
async function cargarPublicaciones() {
    const publicacionesDiv = document.getElementById('publicaciones');
    publicacionesDiv.innerHTML = 'Cargando publicaciones...';

    try {
        const response = await fetch(`${apiBaseUrl}/publicaciones`);
        if (response.ok) {
            const publicaciones = await response.json();
            publicacionesDiv.innerHTML = publicaciones
                .map(
                    (pub) =>
                        `<div>
                            <p><strong>ID:</strong> ${pub.id}</p>
                            <p><strong>Usuario:</strong> ${pub.idUsuario}</p>
                            <p><strong>Fecha:</strong> ${pub.fechaCreacion}</p>
                            <p><strong>Me Gusta:</strong> ${pub.cantidadMeGusta}</p>
                            <button onclick="darMeGusta('${pub.id}')">Me gusta</button>
                            <hr />
                        </div>`
                )
                .join('');
        } else {
            publicacionesDiv.innerHTML = 'Error al cargar publicaciones.';
        }
    } catch (error) {
        console.error('Error al cargar publicaciones:', error);
        publicacionesDiv.innerHTML = 'Error al conectar con el servidor.';
    }
}

// Crear una publicación
async function crearPublicacion() {
    const contenido = prompt('Escribe el contenido de tu publicación:');
    if (!contenido) return alert('El contenido no puede estar vacío.');

    const publicacion = { idUsuario: 1, contenido }; // Asumimos el usuario 1

    try {
        const response = await fetch(`${apiBaseUrl}/publicaciones`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(publicacion)
        });

        if (response.ok) {
            alert('Publicación creada exitosamente.');
            cargarPublicaciones();
        } else {
            alert('Error al crear la publicación.');
        }
    } catch (error) {
        console.error('Error al crear publicación:', error);
        alert('Ocurrió un error al crear la publicación.');
    }
}

// Dar "Me gusta" a una publicación
async function darMeGusta(idPublicacion) {
    const meGusta = { idUsuario: 1, idPublicacion }; // Asumimos el usuario 1

    try {
        const response = await fetch(`${apiBaseUrl}/me-gusta`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(meGusta)
        });

        if (response.ok) {
            alert('¡Te ha gustado esta publicación!');
            cargarPublicaciones();
        } else {
            alert('Error al dar me gusta.');
        }
    } catch (error) {
        console.error('Error al dar me gusta:', error);
        alert('Ocurrió un error al dar me gusta.');
    }
}

// Cargar perfil del usuario
async function cargarPerfil() {
    const infoPerfil = document.getElementById('infoPerfil');

    try {
        const response = await fetch(`${apiBaseUrl}/usuarios/1`); // Asumimos usuario 1
        if (response.ok) {
            const usuario = await response.json();
            infoPerfil.innerHTML = `
                <p><strong>Nombre:</strong> ${usuario.username}</p>
                <p><strong>Email:</strong> ${usuario.email}</p>
            `;
        } else {
            infoPerfil.innerHTML = 'Error al cargar el perfil.';
        }
    } catch (error) {
        console.error('Error al cargar el perfil:', error);
        infoPerfil.innerHTML = 'Error al conectar con el servidor.';
    }
}

// Cerrar sesión
function cerrarSesion() {
    alert('Sesión cerrada.');
    mostrarSeccion('registro');
}
