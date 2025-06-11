<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
  #overlay-username-error {
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

  #toast-username-error {
    background: #f1c40f; /* amarillo advertencia */
    color: #2c3e50;
    padding: 25px 40px;
    border-radius: 15px;
    box-shadow: 0 8px 15px rgba(241, 196, 15, 0.5);
    display: flex;
    align-items: center;
    gap: 20px;
    animation: fadeInScale 0.5s ease forwards;
    max-width: 420px;
    font-size: 20px;
    font-weight: 600;
  }

  #toast-username-error svg {
    width: 40px;
    height: 40px;
    fill: #2c3e50;
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

<div id="overlay-username-error">
  <div id="toast-username-error" role="alert" aria-live="assertive" aria-atomic="true">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
      <path d="M12 2a10 10 0 1 0 10 10A10.011 10.011 0 0 0 12 2zm1 15h-2v-2h2zm0-4h-2V7h2z"/>
    </svg>
    Advertencia: Nombre de usuario ya existe
  </div>
</div>

<script>
  setTimeout(() => {
    const overlay = document.getElementById('overlay-username-error');
    overlay.style.opacity = '0';
    overlay.style.transition = 'opacity 0.5s ease';
    setTimeout(() => overlay.style.display = 'none', 400);
  }, 1400);

  // Limpiar el parámetro 'msg=username' de la URL
  if (window.history.replaceState) {
    const url = new URL(window.location);
    url.searchParams.delete("msg");
    window.history.replaceState({}, document.title, url.pathname);
  }
</script>
