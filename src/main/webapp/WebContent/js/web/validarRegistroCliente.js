document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("formRegistroCliente");
  const nombre = document.getElementById("nombre");
  const apellidos = document.getElementById("apellidos");
  const dni = document.getElementById("dni");
  const telefono = document.getElementById("telefono");
  const correo = document.getElementById("correo");
  const password = document.getElementById("password");
  const confirmar = document.getElementById("confirmar");

  const caracteresProhibidos = /[<>*\/\\'"`]/g;

  // Solo letras para nombre y apellidos (más bloqueo de especiales)
  [nombre, apellidos].forEach(input => {
    input.addEventListener("input", () => {
      input.value = input.value
        .replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ\s]/g, "")
        .replace(caracteresProhibidos, "");
    });
  });

  // DNI - solo números (8)
  dni.addEventListener("input", () => {
    dni.value = dni.value.replace(/\D/g, "").slice(0, 8);
  });

  // Teléfono peruano: debe iniciar con 9
  telefono.addEventListener("input", () => {
    let valor = telefono.value.replace(/\D/g, "");
    if (valor.length > 0 && valor.charAt(0) !== '9') {
      valor = ''; // si no inicia con 9, se borra
    }
    telefono.value = valor.slice(0, 9);
  });

  // Bloquear caracteres especiales peligrosos en correo
  correo.addEventListener("input", () => {
    correo.value = correo.value.replace(caracteresProhibidos, "");
  });

  // Contraseñas: bloquear caracteres especiales peligrosos
  [password, confirmar].forEach(input => {
    input.addEventListener("input", () => {
      input.value = input.value.replace(caracteresProhibidos, "");
    });
  });

  // Validación final antes de enviar
  form.addEventListener("submit", function (e) {
    if (password.value !== confirmar.value) {
      e.preventDefault();
      alert("Las contraseñas no coinciden.");
      confirmar.focus();
      return;
    }

    if (caracteresProhibidos.test(password.value) || caracteresProhibidos.test(confirmar.value)) {
      e.preventDefault();
      alert("Las contraseñas no deben contener caracteres especiales como < > * / \\ ' \" `");
      return;
    }

    if (caracteresProhibidos.test(correo.value)) {
      e.preventDefault();
      alert("El correo electrónico no debe contener caracteres especiales como < > * / \\ ' \" `");
      correo.focus();
      return;
    }

    if (dni.value.length !== 8) {
      e.preventDefault();
      alert("El DNI debe tener exactamente 8 dígitos.");
      dni.focus();
      return;
    }

    if (!/^9\d{8}$/.test(telefono.value)) {
      e.preventDefault();
      alert("El número de teléfono debe comenzar con 9 y tener exactamente 9 dígitos.");
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
