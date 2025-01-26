/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    // Verificar si el usuario está autenticado
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = '/registro';
        return;
    }

    // Elementos principales
    const postsFeed = document.getElementById('postsFeed');
    const createPostForm = document.getElementById('createPostForm');
    const logoutBtn = document.getElementById('logoutBtn');

    // Cargar publicaciones iniciales
    loadPosts();

    // Función para cargar publicaciones
    async function loadPosts() {
        try {
            const response = await fetch('http://localhost:8090/publicaciones', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    contenido: content,
                    fechaCreacion: new Date().toISOString(),
                }),
                credentials: 'include',
            });

            if (!response.ok)
                throw new Error('Error al cargar publicaciones');

            const posts = await response.json();
            renderPosts(posts);
        } catch (error) {
            console.error('Error al cargar publicaciones:', error);
            showMessage('error', 'No se pudieron cargar las publicaciones.');
        }
    }

    // Renderizar publicaciones
    function renderPosts(posts) {
        postsFeed.innerHTML = '';
        if (posts.length === 0) {
            postsFeed.innerHTML = '<p class="text-gray-500 text-center">No hay publicaciones aún.</p>';
            return;
        }
        posts.forEach(post => createPostElement(post));
    }

    // Crear elemento de publicación
    function createPostElement(post) {
        const postElement = document.createElement('div');
        postElement.className = 'bg-white rounded-lg shadow-md p-6 mb-4 fade-in';
        postElement.innerHTML = `
        <div class="flex items-center mb-4">
            <img src="${post.urlImagenPerfil || `https://ui-avatars.com/api/?name=${post.autor}`}" 
                 alt="Avatar" 
                 class="w-10 h-10 rounded-full mr-3">
            <div>
                <h3 class="font-semibold">Autor: ${post.autor || 'Usuario desconocido'}</h3>
                <p class="text-gray-500 text-sm">${new Date(post.fechaCreacion).toLocaleString()}</p>
            </div>
        </div>
        <p class="text-gray-800 mb-4">${post.contenido || ''}</p>
        <div class="flex items-center space-x-4">
            <button class="action-button flex items-center space-x-2 text-gray-500" 
                    onclick="handleLike('${post.id}')">
                <i class="fas fa-heart"></i>
                <span>${post.cantidadMeGusta || 0}</span>
            </button>
            <button class="action-button flex items-center space-x-2 text-gray-500"
                    onclick="toggleComments('${post.id}')">
                <i class="fas fa-comment"></i>
                <span>Comentarios</span>
            </button>
        </div>
    `;
        postsFeed.appendChild(postElement);
    }

    // Renderizar comentarios
    function renderComments(comments) {
        if (!comments?.length)
            return '<p class="text-gray-500 text-sm">No hay comentarios aún.</p>';
        return comments.map(comment => `
            <div class="bg-gray-50 p-3 rounded-lg">
                <p class="font-medium text-sm">${comment.username || 'Usuario'}</p>
                <p class="text-gray-600 text-sm mt-1">${comment.contenido}</p>
            </div>
        `).join('');
    }

    // Crear publicación
    createPostForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const content = document.getElementById('postContent').value.trim();

        if (!content) {
            showMessage('error', 'El contenido de la publicación no puede estar vacío.');
            return;
        }

        const payload = {contenido: content}; // JSON sin idUsuario
        console.log('Datos enviados al servidor:', payload);

        try {
            const response = await fetch('http://localhost:8090/publicaciones', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload),
                credentials: 'include',
            });

            if (!response.ok) {
                throw new Error('Error al crear la publicación');
            }

            showMessage('success', '¡Publicación creada!');
            loadPosts();
        } catch (error) {
            console.error('Error al crear publicación:', error);
            showMessage('error', 'No se pudo crear la publicación.');
        }
    });

    // Manejar "Me gusta"
    window.handleLike = async (postId) => {
        try {
            const response = await fetch('http://localhost:8090/me-gusta', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    idPublicacion: postId,
                }),
            });

            if (!response.ok) {
                throw new Error('Error al dar me gusta');
            }

            // Recargar todas las publicaciones
            await loadPosts();
            showMessage('success', '¡Me gusta agregado!');
        } catch (error) {
            console.error('Error al dar me gusta:', error);
            showMessage('error', 'No se pudo dar me gusta.');
        }
    };

    // Manejar comentarios
    window.handleComment = async (e, postId) => {
        e.preventDefault();
        const content = e.target.querySelector('textarea').value.trim();

        if (!content) {
            showMessage('error', 'El comentario no puede estar vacío.');
            return;
        }

        try {
            const response = await fetch('http://localhost:8090/comentarios', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    idPublicacion: postId,
                    contenido: content,
                }),
            });

            if (!response.ok) {
                throw new Error('Error al agregar comentario');
            }

            // Recargar todas las publicaciones
            await loadPosts();
            e.target.querySelector('textarea').value = '';
            showMessage('success', '¡Comentario agregado!');
        } catch (error) {
            console.error('Error al agregar comentario:', error);
            showMessage('error', 'No se pudo agregar el comentario.');
        }
    };
    

    // Alternar visibilidad de comentarios
    window.toggleComments = (postId) => {
        const commentsSection = document.getElementById(`comments-${postId}`);
        commentsSection.classList.toggle('hidden');
    };

    // Cerrar sesión
    logoutBtn.addEventListener('click', () => {
        localStorage.removeItem('user');
        window.location.href = '/registro';
    });

    // Mostrar mensajes
    function showMessage(type, text) {
        const message = document.createElement('div');
        message.className = `${type}-message fade-in fixed top-4 right-4 p-4 rounded-lg shadow-lg`;
        message.textContent = text;
        document.body.appendChild(message);

        setTimeout(() => {
            message.remove();
        }, 3000);
    }
});

