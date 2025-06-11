<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
  #overlay-error-rol {
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

  #toast-error-rol {
    background: #e67e22; /* anaranjado intenso */
    color: white;
    padding: 25px 40px;
    border-radius: 15px;
    box-shadow: 0 8px 15px rgba(230, 126, 34, 0.5);
    display: flex;
    align-items: center;
    gap: 20px;
    animation: fadeInScale 0.5s ease forwards;
    max-width: 420px;
    font-size: 20px;
    font-weight: 600;
  }

  #toast-error-rol svg {
    width: 40px;
    height: 40px;
    fill: white;
    flex-shrink: 0;
  }

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

<div id="overlay-error-rol">
  <div id="toast-error-rol" role="alert" aria-live="assertive" aria-atomic="true">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
      <path d="M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z"/>
    </svg>
    Error: Rol ya registrado
  </div>
</div>

<script>
  // Ocultar automáticamente
  setTimeout(() => {
    const overlay = document.getElementById('overlay-error-rol');
    overlay.style.opacity = '0';
    overlay.style.transition = 'opacity 0.5s ease';
    setTimeout(() => overlay.style.display = 'none', 400);
  }, 1400);

  // Limpiar el parámetro 'msg=rolDuplicado' de la URL
  if (window.history.replaceState) {
    const url = new URL(window.location);
    url.searchParams.delete("msg");
    window.history.replaceState({}, document.title, url.pathname);
  }
</script>
