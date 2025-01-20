/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    // Verificar si el usuario está autenticado
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = '/views/login/login.html';
        return;
    }
    
    // Configurar el link del perfil
    const profileLink = document.getElementById('profileLink');
    if (profileLink) {
        profileLink.href = '/perfil';  // Esta es la línea que mencioné
    }

    const postsFeed = document.getElementById('postsFeed');
    const createPostForm = document.getElementById('createPostForm');
    const logoutBtn = document.getElementById('logoutBtn');

    // Cargar publicaciones
    async function loadPosts() {
        try {
            const response = await fetch('http://localhost:8090/publicaciones');
            const posts = await response.json();
            
            postsFeed.innerHTML = '';
            posts.forEach(post => {
                createPostElement(post);
            });
        } catch (error) {
            console.error('Error al cargar publicaciones:', error);
            showMessage('error', 'Error al cargar las publicaciones');
        }
    }

    // Crear elemento de publicación
    function createPostElement(post) {
        const postElement = document.createElement('div');
        postElement.className = 'bg-white rounded-lg shadow-md p-6 fade-in';
        
        postElement.innerHTML = `
            <div class="flex items-center mb-4">
                <img src="https://ui-avatars.com/api/?name=${post.idUsuario}&background=random" 
                     alt="Avatar" 
                     class="w-10 h-10 rounded-full mr-3">
                <div>
                    <h3 class="font-semibold">${post.username || 'Usuario'}</h3>
                    <p class="text-gray-500 text-sm">${new Date(post.fechaCreacion).toLocaleString()}</p>
                </div>
            </div>
            <p class="text-gray-800 mb-4">${post.contenido}</p>
            <div class="flex items-center space-x-4">
                <button class="action-button flex items-center space-x-2 text-gray-500 hover:text-red-500" 
                        onclick="handleLike('${post.id}')">
                    <i class="fas fa-heart ${post.liked ? 'text-red-500' : ''}"></i>
                    <span>${post.cantidadMeGusta || 0}</span>
                </button>
                <button class="action-button flex items-center space-x-2 text-gray-500 hover:text-blue-500"
                        onclick="toggleComments('${post.id}')">
                    <i class="fas fa-comment"></i>
                    <span>Comentar</span>
                </button>
            </div>
            <div id="comments-${post.id}" class="comments-section hidden mt-4">
                <form onsubmit="handleComment(event, '${post.id}')" class="mb-4">
                    <textarea
                        class="w-full p-2 border rounded-lg resize-none focus:ring-2 focus:ring-purple-500"
                        placeholder="Escribe un comentario..."
                        rows="2"
                    ></textarea>
                    <button type="submit" class="mt-2 bg-purple-600 text-white px-4 py-2 rounded-md hover:bg-purple-700">
                        Comentar
                    </button>
                </form>
                <div class="comments-list space-y-2">
                    <!-- Los comentarios se cargarán aquí -->
                </div>
            </div>
        `;

        postsFeed.appendChild(postElement);
    }

    // Manejar creación de publicación
    createPostForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const content = document.getElementById('postContent').value.trim();
        
        if (!content) return;

        try {
            const response = await fetch('http://localhost:8090/publicaciones', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    idUsuario: user.id,
                    contenido: content,
                    fechaCreacion: new Date().toISOString()
                })
            });

            if (!response.ok) throw new Error('Error al crear la publicación');

            document.getElementById('postContent').value = '';
            showMessage('success', 'Publicación creada exitosamente');
            loadPosts();
        } catch (error) {
            console.error('Error:', error);
            showMessage('error', 'Error al crear la publicación');
        }
    });

    // Manejar likes
    window.handleLike = async (postId) => {
        try {
            await fetch('http://localhost:8090/me-gusta', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    idPublicacion: postId,
                    idUsuario: user.id
                })
            });

            loadPosts();
        } catch (error) {
            console.error('Error:', error);
            showMessage('error', 'Error al dar me gusta');
        }
    };

    // Manejar comentarios
    window.handleComment = async (e, postId) => {
        e.preventDefault();
        const content = e.target.querySelector('textarea').value.trim();
        
        if (!content) return;

        try {
            await fetch('http://localhost:8090/comentarios', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    idPublicacion: postId,
                    idUsuario: user.id,
                    contenido: content
                })
            });

            e.target.querySelector('textarea').value = '';
            loadPosts();
        } catch (error) {
            console.error('Error:', error);
            showMessage('error', 'Error al comentar');
        }
    };

    // Toggle sección de comentarios
    window.toggleComments = (postId) => {
        const commentsSection = document.getElementById(`comments-${postId}`);
        commentsSection.classList.toggle('hidden');
    };

    // Cerrar sesión
    logoutBtn.addEventListener('click', () => {
        localStorage.removeItem('user');
        window.location.href = '/views/login/login.html';
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

    // Cargar publicaciones iniciales
    loadPosts();
});// feed.js
document.addEventListener('DOMContentLoaded', () => {
    // Verificar autenticación
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = '/registro';
        return;
    }

    const postsFeed = document.getElementById('postsFeed');
    const createPostForm = document.getElementById('createPostForm');
    const logoutBtn = document.getElementById('logoutBtn');

    // Cargar publicaciones
    async function loadPosts() {
        try {
            const response = await fetch('http://localhost:8090/publicaciones');
            const posts = await response.json();
            
            // Obtener información de likes y comentarios para cada publicación
            const enhancedPosts = await Promise.all(posts.map(async (post) => {
                const [likesResponse, commentsResponse, userResponse] = await Promise.all([
                    fetch(`http://localhost:8090/me-gusta/publicacion/${post.id}`),
                    fetch(`http://localhost:8090/comentarios/publicacion/${post.id}`),
                    fetch(`http://localhost:8090/usuarios/${post.idUsuario}`)
                ]);

                const likes = await likesResponse.json();
                const comments = await commentsResponse.json();
                const userData = await userResponse.json();

                return {
                    ...post,
                    likes,
                    comments,
                    username: userData.username,
                    liked: likes.some(like => like.idUsuario === user.id)
                };
            }));

            renderPosts(enhancedPosts);
        } catch (error) {
            showMessage('error', 'Error al cargar las publicaciones');
        }
    }

    // Renderizar publicaciones
    function renderPosts(posts) {
        postsFeed.innerHTML = '';
        posts.forEach(post => createPostElement(post));
    }

    // Crear elemento de publicación
    function createPostElement(post) {
        const postElement = document.createElement('div');
        postElement.className = 'bg-white rounded-lg shadow-md p-6 mb-4 fade-in';
        
        postElement.innerHTML = `
            <div class="flex items-center mb-4">
                <img src="${post.urlImagenPerfil || `https://ui-avatars.com/api/?name=${post.username}`}" 
                     alt="Avatar" 
                     class="w-10 h-10 rounded-full mr-3">
                <div>
                    <h3 class="font-semibold">${post.username}</h3>
                    <p class="text-gray-500 text-sm">${new Date(post.fechaCreacion).toLocaleString()}</p>
                </div>
            </div>
            <p class="text-gray-800 mb-4">${post.contenido || ''}</p>
            <div class="flex items-center space-x-4">
                <button class="action-button flex items-center space-x-2 ${post.liked ? 'text-red-500' : 'text-gray-500'}" 
                        onclick="handleLike('${post.id}')">
                    <i class="fas fa-heart"></i>
                    <span>${post.likes?.length || 0}</span>
                </button>
                <button class="action-button flex items-center space-x-2 text-gray-500"
                        onclick="toggleComments('${post.id}')">
                    <i class="fas fa-comment"></i>
                    <span>${post.comments?.length || 0} Comentarios</span>
                </button>
            </div>
            <div id="comments-${post.id}" class="comments-section hidden mt-4">
                <form onsubmit="handleComment(event, '${post.id}')" class="mb-4">
                    <textarea
                        class="w-full p-2 border rounded-lg resize-none focus:ring-2 focus:ring-purple-500"
                        placeholder="Escribe un comentario..."
                        rows="2"
                    ></textarea>
                    <button type="submit" class="mt-2 bg-purple-600 text-white px-4 py-2 rounded-md hover:bg-purple-700">
                        Comentar
                    </button>
                </form>
                <div class="comments-list space-y-2">
                    ${renderComments(post.comments)}
                </div>
            </div>
        `;

        postsFeed.appendChild(postElement);
    }

    // Renderizar comentarios
    function renderComments(comments) {
        if (!comments?.length) return '<p class="text-gray-500 text-sm">No hay comentarios aún</p>';
        
        return comments.map(comment => `
            <div class="bg-gray-50 p-3 rounded-lg">
                <div class="flex items-start space-x-3">
                    <div class="flex-1">
                        <p class="font-medium text-sm">${comment.username || 'Usuario'}</p>
                        <p class="text-gray-600 text-sm mt-1">${comment.contenido}</p>
                    </div>
                </div>
            </div>
        `).join('');
    }

    // Crear publicación
    createPostForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const content = document.getElementById('postContent').value.trim();
        
        if (!content) return;

        try {
            const response = await fetch('http://localhost:8090/publicaciones', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    idUsuario: user.id,
                    contenido: content,
                    fechaCreacion: new Date().toISOString(),
                    cantidadMeGusta: 0
                })
            });

            if (!response.ok) throw new Error('Error al crear la publicación');

            document.getElementById('postContent').value = '';
            showMessage('success', 'Publicación creada exitosamente');
            loadPosts();
        } catch (error) {
            showMessage('error', 'Error al crear la publicación');
        }
    });

    // Manejar likes
    window.handleLike = async (postId) => {
        try {
            const response = await fetch('http://localhost:8090/me-gusta', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    idPublicacion: postId,
                    idUsuario: user.id,
                    fechaCreacion: new Date().toISOString()
                })
            });

            if (!response.ok) throw new Error('Error al dar me gusta');
            loadPosts();
        } catch (error) {
            showMessage('error', 'Error al dar me gusta');
        }
    };

    // Manejar comentarios
    window.handleComment = async (e, postId) => {
        e.preventDefault();
        const content = e.target.querySelector('textarea').value.trim();
        
        if (!content) return;

        try {
            const response = await fetch('http://localhost:8090/comentarios', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    idPublicacion: postId,
                    idUsuario: user.id,
                    contenido: content,
                    fechaCreacion: new Date().toISOString()
                })
            });

            if (!response.ok) throw new Error('Error al comentar');

            e.target.querySelector('textarea').value = '';
            loadPosts();
        } catch (error) {
            showMessage('error', 'Error al comentar');
        }
    };

    // Toggle comentarios
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

    // Cargar publicaciones iniciales
    loadPosts();
});

