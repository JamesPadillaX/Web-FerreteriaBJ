<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

<!-- === ASISTENTE VIRTUAL FLOTANTE === -->
<div id="chatbot-container">
    <div id="chatbot-header">
        <span>🤖 Asistente BJ</span>
        <button id="close-chatbot">✖</button>
    </div>

    <div id="chatbot-body">
        <div class="chat-message bot">
            👋 ¡Hola! Soy el asistente de <strong>Ferretería BJ</strong>.<br>
            ¿Qué deseas hacer hoy? (por ejemplo: buscar un producto, ver ofertas, consultar stock…)
        </div>
    </div>

    <div id="chatbot-input">
        <input type="text" id="userMessage" placeholder="Escribe tu mensaje..." />
        <button id="sendMessage"><i class="fas fa-paper-plane"></i></button>
    </div>
</div>

<!-- === BOTÓN FLOTANTE === -->
<button id="chatbot-toggle"><i class="fas fa-comments"></i></button>

<!-- === ESTILOS === -->
<style>
    /* === BOTÓN FLOTANTE === */
    #chatbot-toggle {
        position: fixed;
        bottom: 25px;
        right: 25px;
        background: #2ea1ff;
        color: #fff;
        border: none;
        border-radius: 50%;
        width: 65px;
        height: 65px;
        font-size: 26px;
        cursor: pointer;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.25);
        transition: all 0.3s ease;
        z-index: 9999;
    }

    #chatbot-toggle:hover {
        transform: scale(1.1);
        background: #0077cc;
    }

    /* === CONTENEDOR DEL CHAT === */
    #chatbot-container {
        position: fixed;
        bottom: 100px;
        right: 25px;
        width: 350px;
        max-height: 500px;
        background: #ffffff;
        border-radius: 18px;
        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
        display: none;
        flex-direction: column;
        overflow: hidden;
        z-index: 10000;
        animation: fadeIn 0.3s ease;
    }

    @keyframes fadeIn {
        from {opacity: 0; transform: translateY(20px);}
        to {opacity: 1; transform: translateY(0);}
    }

    /* === CABECERA === */
    #chatbot-header {
        background: #2ea1ff;
        color: white;
        padding: 14px 18px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-weight: 600;
        font-size: 1rem;
    }

    #close-chatbot {
        background: transparent;
        border: none;
        color: white;
        font-size: 18px;
        cursor: pointer;
    }

    /* === CUERPO === */
    #chatbot-body {
        flex: 1;
        padding: 14px;
        overflow-y: auto;
        background: #f9f9f9;
        display: flex;
        flex-direction: column;
    }

    .chat-message {
        margin: 8px 0;
        padding: 10px 14px;
        border-radius: 12px;
        max-width: 80%;
        line-height: 1.4;
    }

    .chat-message.bot {
        background: #e6f3ff;
        align-self: flex-start;
    }

    .chat-message.user {
        background: #2ea1ff;
        color: white;
        align-self: flex-end;
    }

    /* === INPUT === */
    #chatbot-input {
        display: flex;
        border-top: 1px solid #ddd;
        background: #fff;
    }

    #chatbot-input input {
        flex: 1;
        border: none;
        padding: 12px;
        font-size: 0.95rem;
        outline: none;
    }

    #chatbot-input button {
        background: #2ea1ff;
        border: none;
        color: white;
        padding: 0 18px;
        font-size: 1.1rem;
        cursor: pointer;
        transition: background 0.3s ease;
    }

    #chatbot-input button:hover {
        background: #0077cc;
    }
</style>

<!-- === SCRIPT === -->
<script>
    const toggleBtn = document.getElementById('chatbot-toggle');
    const chatbot = document.getElementById('chatbot-container');
    const closeBtn = document.getElementById('close-chatbot');
    const sendBtn = document.getElementById('sendMessage');
    const input = document.getElementById('userMessage');
    const body = document.getElementById('chatbot-body');

    // Mostrar / ocultar
    toggleBtn.addEventListener('click', () => {
        chatbot.style.display = chatbot.style.display === 'flex' ? 'none' : 'flex';
    });

    closeBtn.addEventListener('click', () => {
        chatbot.style.display = 'none';
    });

    // Enviar mensaje
    sendBtn.addEventListener('click', enviarMensaje);
    input.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') enviarMensaje();
    });

    function enviarMensaje() {
        const texto = input.value.trim();
        if (texto === '') return;
        agregarMensaje(texto, 'user');
        input.value = '';

        setTimeout(() => {
            agregarMensaje(respuestaAutomatica(texto), 'bot');
        }, 600);
    }

    function agregarMensaje(texto, tipo) {
        const div = document.createElement('div');
        div.classList.add('chat-message', tipo);
        div.innerHTML = texto;
        body.appendChild(div);
        body.scrollTop = body.scrollHeight;
    }

    // Respuestas básicas (puedes personalizar)
    function respuestaAutomatica(mensaje) {
        const texto = mensaje.toLowerCase();
        if (texto.includes('hola')) return '👋 ¡Hola! ¿Buscas algo en específico?';
        if (texto.includes('oferta')) return '🎉 Tenemos ofertas especiales en herramientas eléctricas 🔧';
        if (texto.includes('horario')) return '🕐 Nuestro horario es de 8:00 a 18:00, de lunes a sábado.';
        if (texto.includes('contacto')) return '📞 Puedes contactarnos al 999-123-456 o por WhatsApp.';
        if (texto.includes('ayuda')) return '💡 Puedo ayudarte a buscar productos o mostrarte nuestras promociones.';
        return '🤔 No estoy seguro de entenderte, pero puedo ayudarte a buscar un producto.';
    }
</script>
