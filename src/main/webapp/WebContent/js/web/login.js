// Limpiar parámetros "error" y "correo" de la URL después de cargar la página
window.addEventListener('load', () => {
  if (window.history.replaceState) {
    const url = new URL(window.location);
    if (url.searchParams.has('error') || url.searchParams.has('correo')) {
      url.searchParams.delete('error');
      url.searchParams.delete('correo');
      window.history.replaceState(null, null, url.pathname);
    }
  }
});

// Funcionalidad del ícono para mostrar/ocultar contraseña
document.addEventListener("DOMContentLoaded", function () {
  const toggleIcons = document.querySelectorAll(".toggle-password");

  toggleIcons.forEach(icon => {
    icon.addEventListener("click", function () {
      const input = this.closest('.input-contrasena').querySelector('input');
      const isPassword = input.type === "password";
      input.type = isPassword ? "text" : "password";

      this.classList.toggle("fa-eye");
      this.classList.toggle("fa-eye-slash");
    });
  });
});

