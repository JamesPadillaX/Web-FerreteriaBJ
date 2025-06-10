document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("formRegistroCliente");
  const nombre = document.getElementById("nombre");
  const apellidos = document.getElementById("apellidos");
  const dni = document.getElementById("dni");
  const telefono = document.getElementById("telefono");
  const password = document.getElementById("password");
  const confirmar = document.getElementById("confirmar");

  // Solo letras para nombre y apellidos
  [nombre, apellidos].forEach(input => {
    input.addEventListener("input", () => {
      input.value = input.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ\s]/g, "");
    });
  });

  // Solo números para DNI y Teléfono con longitud máxima
  dni.addEventListener("input", () => {
    dni.value = dni.value.replace(/\D/g, "").slice(0, 8);
  });

  telefono.addEventListener("input", () => {
    telefono.value = telefono.value.replace(/\D/g, "").slice(0, 9);
  });

  // Validación al enviar
  form.addEventListener("submit", function (e) {
    if (password.value !== confirmar.value) {
      e.preventDefault();
      alert("Las contraseñas no coinciden.");
      confirmar.focus();
      return;
    }

    if (dni.value.length !== 8) {
      e.preventDefault();
      alert("El DNI debe tener exactamente 8 dígitos.");
      dni.focus();
      return;
    }

    if (telefono.value.length !== 9) {
      e.preventDefault();
      alert("El teléfono debe tener exactamente 9 dígitos.");
      telefono.focus();
      return;
    }
  });
});

// Mostrar/Ocultar contraseña
function togglePassword(inputId, icon) {
  const input = document.getElementById(inputId);
  const isPassword = input.type === "password";
  input.type = isPassword ? "text" : "password";
  icon.classList.toggle("fa-eye");
  icon.classList.toggle("fa-eye-slash");
}
