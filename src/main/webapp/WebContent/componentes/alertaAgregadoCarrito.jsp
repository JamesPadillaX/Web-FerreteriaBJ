<style>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@500&display=swap');

  #toast-agregado {
    position: fixed;
    bottom: 24px;
    left: 50%;
    transform: translateX(-50%) translateY(100px);
    background-color: #FFC107; /* Amarillo */
    color: #000; 
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
    #toast-agregado {
      font-size: 14px;
      padding: 10px 16px;
    }
  }
</style>

<div id="toast-agregado">Producto agregado al carrito correctamente</div>

<script>
  const toast = document.getElementById("toast-agregado");
  if (toast) {
    setTimeout(() => {
      toast.style.animation = "fadeOutDown 0.5s ease forwards";
    }, 2500);

    if (window.history.replaceState) {
      const url = new URL(window.location);
      url.searchParams.delete("msg");
      window.history.replaceState({}, document.title, url.pathname + url.search);
    }
  }
</script>
