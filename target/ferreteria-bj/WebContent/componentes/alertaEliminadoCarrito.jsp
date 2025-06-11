<style>
  #overlay-eliminado {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0,0,0,0.4);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }

  #toast-eliminado {
    background: #e74c3c; /* rojo alerta */
    color: white;
    padding: 25px 40px;
    border-radius: 15px;
    box-shadow: 0 8px 15px rgba(231, 76, 60, 0.5);
    display: flex;
    align-items: center;
    gap: 20px;
    animation: fadeInScale 0.5s ease forwards;
    max-width: 400px;
    font-size: 22px;
    font-weight: 600;
  }

  /* Ícono de check */
  #toast-eliminado svg {
    width: 40px;
    height: 40px;
    fill: white;
    flex-shrink: 0;
  }

  /* Animación */
  @keyframes fadeInScale {
    0% {
      opacity: 0;
      transform: scale(0.7);
    }
    100% {
      opacity: 1;
      transform: scale(1);
    }
  }
</style>

<!-- Contenedor de alerta -->
<div id="overlay-eliminado">
  <div id="toast-eliminado" role="alert" aria-live="assertive" aria-atomic="true">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
      <!-- Ícono de check (puedes cambiar por ícono de papelera si prefieres) -->
      <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"/>
    </svg>
    Producto eliminado correctamente
  </div>
</div>

<script>
  // Ocultar después de 2s
  setTimeout(() => {
    const overlay = document.getElementById('overlay-eliminado');
    overlay.style.opacity = '0';
    overlay.style.transition = 'opacity 0.5s ease';
    setTimeout(() => overlay.style.display = 'none', 400);
  }, 1400);

  // Limpiar ?msg=eliminado de la URL
  if (window.history.replaceState) {
    const url = new URL(window.location);
    url.searchParams.delete("msg");
    window.history.replaceState({}, document.title, url.pathname + url.search);
  }
</script>
