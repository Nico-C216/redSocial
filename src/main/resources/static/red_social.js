/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    // Verificar autenticación
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = '/registro.html';
        return;
    }

    // Elementos del DOM
    const postsFeed = document.getElementById('postsFeed');
    const createPostForm = document.getElementById('createPostForm');
    const logoutBtn = document.getElementById('logoutBtn');

    // Cargar publicaciones al iniciar
    loadPosts();

    async function loadPosts() {
        try {
            const response = await fetch('http://localhost:8090/publicaciones', {
                method: 'GET',
                credentials: 'include',
            });

            if (!response.ok)
                throw new Error(`Error ${response.status}: No se pudieron cargar las publicaciones`);

            const posts = await response.json();
            console.log("[DEBUG] Loaded posts from API:", posts);  // 🔥 Log fetched posts

            renderPosts(posts);
        } catch (error) {
            console.error('[ERROR] Error loading posts:', error);
            postsFeed.innerHTML = '<p class="text-red-500 text-center">Error al cargar publicaciones.</p>';
        }
    }

    function renderPosts(posts) {
        console.log("[DEBUG] Rendering posts:", posts);  // 🔥 Log post rendering

        postsFeed.innerHTML = posts.length === 0
                ? '<p class="text-gray-500 text-center">No hay publicaciones aún.</p>'
                : '';

        posts.forEach(post => {
            const postElement = document.createElement('div');
            postElement.className = 'bg-white rounded-lg shadow-md p-6 mb-4';
            postElement.id = `post-${post.id}`;

            postElement.innerHTML = `
            <div class="flex items-center mb-4">
                <h3 class="font-semibold">Autor: ${post.autor || 'Desconocido'}</h3>
                <p class="text-gray-500 text-sm">${new Date(post.fechaCreacion).toLocaleString()}</p>
            </div>
            <p class="text-gray-800 mb-4">${post.contenido}</p>
            <div class="flex items-center space-x-4">
                <button class="action-button flex items-center space-x-2 text-gray-500" 
                    onclick="handleLike('${post.id}')">
                    <i class="fas fa-heart"></i>
                    <span id="likes-${post.id}">${post.cantidadMeGusta || 0}</span>
                </button>
                <button class="action-button flex items-center space-x-2 text-gray-500" 
                    onclick="toggleComments('${post.id}')">
                    <i class="fas fa-comment"></i>
                    <span id="comment-count-${post.id}">${post.comentarios ? post.comentarios.length : 0} Comentarios</span>
                </button>
            </div>
            <div id="comments-${post.id}" class="hidden"></div>
        `;

            postsFeed.appendChild(postElement);

            console.log("[DEBUG] Calling renderComments() for post:", post.id, "with comments:", post.comentarios);
            renderComments(post.id, post.comentarios);
        });
    }

    //Funcion de me gusta
    async function handleLike(postId) {
        try {
            const response = await fetch('http://localhost:8090/me-gusta', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include',
                body: JSON.stringify({idPublicacion: postId}),
            });

            if (!response.ok)
                throw new Error('Error al dar me gusta');

            await loadPosts(); // Recargar todas las publicaciones
        } catch (error) {
            console.error('Error al dar me gusta:', error);
        }
    }

    function updatePostInDOM(post) {
        const likesSpan = document.getElementById(`likes-${post.id}`);
        if (likesSpan)
            likesSpan.textContent = post.cantidadMeGusta;
    }

    //Funcion de comentarios
    async function handleComment(e, postId) {
        e.preventDefault();
        const textarea = document.getElementById(`comment-input-${postId}`);

        if (!textarea || !textarea.value.trim()) {
            console.error('[ERROR] Comment content is empty.');
            return;
        }

        try {
            const response = await fetch('http://localhost:8090/comentarios', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include',
                body: JSON.stringify({idPublicacion: postId, contenido: textarea.value.trim()}),
            });

            if (!response.ok)
                throw new Error('Error adding comment');

            textarea.value = ''; // Clear input

            // 🔥 Instead of reloading all posts, just fetch the updated post
            toggleComments(postId);
        } catch (error) {
            console.error('[ERROR] Error adding comment:', error);
        }
    }


    function renderComments(postId, comments) {
        console.log(`[DEBUG] Rendering comments for post ${postId}:`, comments);

        const commentsContainer = document.getElementById(`comments-${postId}`);
        if (!commentsContainer) {
            console.error('[ERROR] Comments container not found for post:', postId);
            return;
        }

        commentsContainer.innerHTML = '';

        if (!comments || comments.length === 0) {
            commentsContainer.innerHTML = '<p class="text-gray-500 text-sm">No hay comentarios aún.</p>';
            return;
        }

        comments.forEach(comment => {
            console.log(`[DEBUG] Rendering comment:`, comment);

            const commentElement = document.createElement('div');
            commentElement.className = 'comment bg-gray-50 p-3 rounded-lg mt-2';
            commentElement.innerHTML = `
            <p class="font-medium text-sm">Usuario</p>
            <p class="text-gray-600 text-sm mt-1">${comment.contenido}</p>
        `;
            commentsContainer.appendChild(commentElement);
        });

        document.getElementById(`comment-count-${postId}`).textContent = `${comments.length} Comentarios`;
        commentsContainer.classList.remove('hidden'); // Show comments
    }


    async function toggleComments(postId) {
        const commentsContainer = document.getElementById(`comments-${postId}`);

        if (!commentsContainer) {
            console.error('[ERROR] Comments container not found:', postId);
            return;
        }

        // 🚀 Fetch updated comments from API
        try {
            const response = await fetch(`http://localhost:8090/publicaciones`);
            if (!response.ok)
                throw new Error('Error fetching posts');

            const posts = await response.json();
            const post = posts.find(p => p.id === postId);

            if (!post) {
                console.error('[ERROR] Post not found:', postId);
                return;
            }

            renderComments(postId, post.comentarios);
        } catch (error) {
            console.error('[ERROR] Failed to fetch comments:', error);
        }

        // 🛠 Toggle visibility
        commentsContainer.classList.toggle('hidden');
    }

    createPostForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const content = document.getElementById('postContent').value.trim();

        if (!content) {
            showMessage('error', 'El contenido de la publicación no puede estar vacío.');
            return;
        }

        try {
            const response = await fetch('http://localhost:8090/publicaciones', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include',
                body: JSON.stringify({contenido: content}),
            });

            if (!response.ok)
                throw new Error('Error al crear la publicación');

            showMessage('success', '¡Publicación creada!');
            loadPosts();
        } catch (error) {
            console.error('Error al crear publicación:', error);
            showMessage('error', 'No se pudo crear la publicación.');
        }
    });

    logoutBtn.addEventListener('click', () => {
        localStorage.removeItem('user');
        window.location.href = '/registro.html';
    });

    function showMessage(type, text) {
        const message = document.createElement('div');
        message.className = `fixed top-4 right-4 p-4 rounded-lg shadow-lg text-white bg-${type === 'error' ? 'red' : 'green'}-500 fade-in`;
        message.textContent = text;
        document.body.appendChild(message);

        setTimeout(() => message.remove(), 3000);
    }
});

