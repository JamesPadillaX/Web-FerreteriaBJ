<style>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@500&display=swap');

  #toast-eliminado {
    position: fixed;
    bottom: 24px;
    left: 50%;
    transform: translateX(-50%) translateY(100px);
    background-color: #00264d; /* Azul marino */
    color: #fff;
    padding: 12px 20px;
    max-width: 90%;
    width: max-content;
    border-radius: 8px;
    font-family: 'Poppins', sans-serif;
    font-size: 15px;
    font-weight: 500;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    opacity: 0;
    pointer-events: none;
    z-index: 9999;
    animation: slideUpFade 0.5s ease-out forwards;
    text-align: center;
    word-break: break-word;
  }

  @keyframes slideUpFade {
    to {
      opacity: 1;
      transform: translateX(-50%) translateY(0);
      pointer-events: auto;
    }
  }

  @keyframes fadeOutDown {
    to {
      opacity: 0;
      transform: translateX(-50%) translateY(100px);
      pointer-events: none;
    }
  }

  @media (max-width: 480px) {
    #toast-eliminado {
      font-size: 14px;
      padding: 10px 16px;
    }
  }
</style>

<div id="toast-eliminado">Listo, agregado correctamente</div>

<script>
  const toast = document.getElementById("toast-eliminado");
  if (toast) {
    // Ocultar automáticamente después de 2.5 segundos
    setTimeout(() => {
      toast.style.animation = "fadeOutDown 0.5s ease forwards";
    }, 2500);

    // Eliminar parámetro "msg" de la URL
    if (window.history.replaceState) {
      const url = new URL(window.location);
      url.searchParams.delete("msg");
      window.history.replaceState({}, document.title, url.pathname + url.search);
    }
  }
</script>
