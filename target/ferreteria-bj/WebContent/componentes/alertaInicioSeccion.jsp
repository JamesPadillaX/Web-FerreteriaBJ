<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  /* Header sin desenfoque, siempre visible y encima */
  #header {
    position: relative;
    z-index: 10001; /* más alto que el overlay */
  }

  /* Contenedor principal del contenido que se desenfocará */
  #contenidoPrincipal {
    transition: filter 0.3s ease, opacity 0.3s ease;
  }

  /* Cuando está activo el desenfoque */
  #contenidoPrincipal.desenfocado {
    filter: blur(5px);
    opacity: 0.6;
    pointer-events: none; /* opcional: evitar interacción */
  }

  /* Capa oscura y translúcida que cubre todo excepto header */
  #overlayFondo {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0,0,0,0.25);
    backdrop-filter: blur(5px);
    z-index: 10000; /* debajo del header, encima del contenido */
  }

  #overlayFondo.visible {
    display: block;
  }

  /* Estilos para la alerta, encima de todo */
  #alertaError {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 10002; /* más arriba que todo */
    opacity: 0;
    visibility: hidden;
    transition: opacity 0.3s ease;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    pointer-events: none;

    display: flex;
    justify-content: center;
    width: 100vw;
    padding: 0 1rem;
    box-sizing: border-box;
  }

  #alertaError.visible {
    opacity: 1;
    visibility: visible;
    pointer-events: auto;
  }

  .alerta-modal {
    background-color: #ffc107;
    color: #0d3b66;
    padding: 1rem 2rem;
    border-radius: 8px;
    box-shadow: 0 6px 12px rgba(255, 193, 7, 0.5);
    max-width: 360px;
    width: 100%;
    text-align: center;
    font-weight: 600;
    font-size: 1.15rem;
    user-select: none;
    letter-spacing: 0.03em;
    line-height: 1.4;
  }
</style>

<!-- Tu header (ejemplo) -->
<header id="header">
</header>

<!-- Contenedor principal para desenfocar -->
<div id="contenidoPrincipal">
</div>

<!-- Overlay para el fondo oscuro + blur -->
<div id="overlayFondo"></div>

<!-- Alerta -->
<div id="alertaError" role="alert" aria-live="assertive" aria-atomic="true">
  <div class="alerta-modal">
    Correo o contraseña incorrectos.
  </div>
</div>

<script>
  (function() {
    const alerta = document.getElementById('alertaError');
    const overlay = document.getElementById('overlayFondo');
    const contenido = document.getElementById('contenidoPrincipal');

    function mostrarAlerta() {
      alerta.classList.add('visible');
      overlay.classList.add('visible');
      contenido.classList.add('desenfocado');
    }

    function ocultarAlerta() {
      alerta.classList.remove('visible');
      overlay.classList.remove('visible');
      contenido.classList.remove('desenfocado');
      setTimeout(() => {
        alerta.style.visibility = 'hidden';
      }, 300);
    }

    document.addEventListener('DOMContentLoaded', () => {
      const params = new URLSearchParams(window.location.search);
      if (params.get('error') === '1') {
        alerta.style.visibility = 'visible';
        mostrarAlerta();

        // Limpiar parámetros para que no reaparezca
        params.delete('error');
        params.delete('correo');
        const newUrl = window.location.pathname + (params.toString() ? '?' + params.toString() : '');
        window.history.replaceState(null, '', newUrl);

        setTimeout(() => {
          ocultarAlerta();
        }, 1500);
      }
    });
  })();
</script>
