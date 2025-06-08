<style>
  /* Fondo overlay */
  #overlay-exito {
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

  /* Contenedor del mensaje */
  #toast-exito {
    background: #3498db; /* azul moderno */
    color: white;
    padding: 25px 40px;
    border-radius: 15px;
    box-shadow: 0 8px 15px rgba(52, 152, 219, 0.5);
    display: flex;
    align-items: center;
    gap: 20px;
    animation: fadeInScale 0.5s ease forwards;
    max-width: 360px;
    font-size: 22px;
    font-weight: 600;
  }

  /* Ícono de check (éxito) */
  #toast-exito svg {
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

<div id="overlay-exito">
  <div id="toast-exito" role="alert" aria-live="assertive" aria-atomic="true">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
      <!-- Ícono de check -->
      <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"/>
    </svg>
    Cambios guardados exitosamente
  </div>
</div>

<script>
  // Ocultar automáticamente después de 3.5 segundos
  setTimeout(() => {
    const overlay = document.getElementById('overlay-exito');
    overlay.style.opacity = '0';
    overlay.style.transition = 'opacity 0.5s ease';
    setTimeout(() => overlay.style.display = 'none', 500);
  }, 2000);

  // Limpiar el parámetro 'msg=guardado' de la URL (puedes cambiar 'msg' si usas otro)
  if (window.history.replaceState) {
    const url = new URL(window.location);
    url.searchParams.delete("msg");
    window.history.replaceState({}, document.title, url.pathname);
  }
</script>
