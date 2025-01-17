/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
const postsContainer = document.getElementById("posts-container");
const newPostContent = document.getElementById("newPostContent");
const createPostBtn = document.getElementById("createPostBtn");

// Cargar publicaciones desde el backend
const cargarPublicaciones = () => {
    fetch("http://localhost:8090/publicaciones")
        .then(response => response.json())
        .then(posts => {
            postsContainer.innerHTML = "";
            posts.forEach(post => {
                const postElement = document.createElement("div");
                postElement.className = "post";
                postElement.innerHTML = `
                    <h4>Usuario: ${post.idUsuario}</h4>
                    <p>${post.contenido || "Sin contenido"}</p>
                    <div class="actions">
                        <button onclick="darMeGusta('${post.id}')">Me gusta (${post.cantidadMeGusta})</button>
                        <button onclick="comentarPost('${post.id}')">Comentar</button>
                    </div>
                `;
                postsContainer.appendChild(postElement);
            });
        })
        .catch(error => console.error("Error al cargar publicaciones:", error));
};

// Crear una nueva publicación
createPostBtn.addEventListener("click", () => {
    const contenido = newPostContent.value.trim();
    if (!contenido) {
        alert("Por favor, escribe algo antes de publicar.");
        return;
    }

    const nuevaPublicacion = {
        idUsuario: "1", // Cambia esto por el ID real del usuario autenticado
        contenido: contenido,
    };

    fetch("http://localhost:8090/publicaciones", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(nuevaPublicacion),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error al crear la publicación.");
            }
            return response.json();
        })
        .then(() => {
            newPostContent.value = "";
            cargarPublicaciones();
        })
        .catch(error => console.error("Error al crear la publicación:", error));
});

// Dar "Me gusta" a una publicación
const darMeGusta = (idPublicacion) => {
    fetch(`http://localhost:8090/me-gusta`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ idPublicacion, idUsuario: "1" }), // ID de usuario autenticado
    })
        .then(() => cargarPublicaciones())
        .catch(error => console.error("Error al dar Me gusta:", error));
};

// Comentar en una publicación
const comentarPost = (idPublicacion) => {
    const comentario = prompt("Escribe tu comentario:");
    if (!comentario) return;

    fetch(`http://localhost:8090/comentarios`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ idPublicacion, idUsuario: "1", contenido: comentario }),
    })
        .then(() => cargarPublicaciones())
        .catch(error => console.error("Error al comentar:", error));
};

// Cargar publicaciones al inicio
cargarPublicaciones();

