<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
  /* Fondo overlay */
  body {
    margin: 0;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  }
  #overlay {
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
  #toast {
    background: #2ecc71; /* verde moderno */
    color: white;
    padding: 25px 40px;
    border-radius: 15px;
    box-shadow: 0 8px 15px rgba(46, 204, 113, 0.5);
    display: flex;
    align-items: center;
    gap: 20px;
    animation: fadeInScale 0.5s ease forwards;
    max-width: 350px;
    font-size: 22px;
    font-weight: 600;
  }

  /* Ícono check */
  #toast svg {
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

<div id="overlay">
  <div id="toast" role="alert" aria-live="assertive" aria-atomic="true">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
      <path d="M20.285 6.709l-11.39 11.386-5.68-5.682 1.414-1.414 4.266 4.265 9.976-9.974z"/>
    </svg>
    Se guardó exitosamente
  </div>
</div>

<script>
  // Ocultar automáticamente después de 3.5 segundos
  setTimeout(() => {
    const overlay = document.getElementById('overlay');
    overlay.style.opacity = '0';
    overlay.style.transition = 'opacity 0.5s ease';
    setTimeout(() => overlay.style.display = 'none', 500);
  }, 2000);

  // Limpiar el parámetro 'msg=exito' de la URL
  if (window.history.replaceState) {
    const url = new URL(window.location);
    url.searchParams.delete("msg");
    window.history.replaceState({}, document.title, url.pathname);
  }
</script>
