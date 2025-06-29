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

// Mostrar/ocultar contraseña
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

  const form = document.getElementById("formLogin");
  const passwordInput = document.getElementById("contrasena");
  const correoInput = document.getElementById("usuario");

  // Bloquear caracteres peligrosos al escribir en contraseña y correo
  [passwordInput, correoInput].forEach(input => {
    input.addEventListener("input", function () {
      this.value = this.value.replace(/[<>'"]/g, "");
    });
  });

  // Validación extra al enviar formulario (por seguridad)
  form.addEventListener("submit", function (e) {
    const password = passwordInput.value;
    const correo = correoInput.value;

    if (/[<>'"]/.test(password)) {
      e.preventDefault();
      alert("La contraseña contiene caracteres no permitidos.");
      return;
    }

    if (/[<>'"]/.test(correo)) {
      e.preventDefault();
      alert("El correo contiene caracteres no permitidos.");
      return;
    }
  });
});
