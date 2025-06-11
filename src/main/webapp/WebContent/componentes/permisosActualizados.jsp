<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
  #overlay-success-permisos {
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

  #toast-success-permisos {
    background: #3498db; /* azul vibrante */
    color: white;
    padding: 25px 40px;
    border-radius: 15px;
    box-shadow: 0 8px 15px rgba(52, 152, 219, 0.5);
    display: flex;
    align-items: center;
    gap: 20px;
    animation: fadeInScale 0.5s ease forwards;
    max-width: 420px;
    font-size: 20px;
    font-weight: 600;
  }

  #toast-success-permisos svg {
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

<div id="overlay-success-permisos">
  <div id="toast-success-permisos" role="alert" aria-live="assertive" aria-atomic="true">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
      <path d="M9 16.2l-3.5-3.5L4 14.2l5 5 12-12-1.4-1.4z"/>
    </svg>
    Permisos actualizados correctamente
  </div>
</div>

<script>
  setTimeout(() => {
    const overlay = document.getElementById('overlay-success-permisos');
    overlay.style.opacity = '0';
    overlay.style.transition = 'opacity 0.5s ease';
    setTimeout(() => overlay.style.display = 'none', 400);
  }, 1400);

  if (window.history.replaceState) {
    const url = new URL(window.location);
    url.searchParams.delete("msg");
    window.history.replaceState({}, document.title, url.pathname);
  }
</script>
