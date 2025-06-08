<style>
  #overlay {
    position: fixed;
    top: 0; left: 0; right: 0; bottom: 0;
    background: rgba(11, 31, 64, 0.3); /* azul oscuro translúcido */
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 9999;
    backdrop-filter: blur(4px);
  }

  #toast {
    background: linear-gradient(135deg, #ffc107, #ffca28);
    color: #0b1f40;
    padding: 30px 50px;
    border-radius: 20px;
    box-shadow: 0 15px 25px rgba(255, 193, 7, 0.6);
    display: flex;
    align-items: center;
    gap: 25px;
    animation: fadeInScale 0.5s ease forwards;
    max-width: 380px;
    font-size: 22px;
    font-weight: 800;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    text-shadow: 0 1px 1px rgba(255, 255, 255, 0.4);
  }

  #toast svg {
    width: 45px;
    height: 45px;
    fill: #0b1f40;
    flex-shrink: 0;
    filter: drop-shadow(0 0 2px rgba(0,0,0,0.2));
  }

  @keyframes fadeInScale {
    0% {
      opacity: 0;
      transform: scale(0.75);
    }
    100% {
      opacity: 1;
      transform: scale(1);
    }
  }
</style>

<div id="overlay" role="alert" aria-live="assertive" aria-atomic="true">
  <div id="toast">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" aria-hidden="true" focusable="false">
      <path d="M20.285 6.709l-11.39 11.386-5.68-5.682 1.414-1.414 4.266 4.265 9.976-9.974z"/>
    </svg>
    Inicio de sesión exitoso
  </div>
</div>

<script>
  // Mostrar el toast solo una vez por sesión
  if (!sessionStorage.getItem('toastShown')) {
    sessionStorage.setItem('toastShown', 'true');

    setTimeout(() => {
      const overlay = document.getElementById('overlay');
      overlay.style.opacity = '0';
      overlay.style.transition = 'opacity 0.5s ease';
      setTimeout(() => overlay.style.display = 'none', 500);
    }, 3000);
  } else {
    // Ya se mostró antes, ocultar inmediatamente
    const overlay = document.getElementById('overlay');
    if (overlay) {
      overlay.style.display = 'none';
    }
  }

  // Eliminar parámetro msg de la URL sin recargar
  if (window.history.replaceState) {
    const url = new URL(window.location);
    if (url.searchParams.has("msg")) {
      url.searchParams.delete("msg");
      window.history.replaceState({}, document.title, url.pathname);
    }
  }
</script>
