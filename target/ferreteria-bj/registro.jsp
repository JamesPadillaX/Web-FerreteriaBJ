<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Registro - Ferretería BJ</title>
  <link rel="stylesheet" href="WebContent/css/web/registro.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

    <main>
      <section>
        <form action="RegistrarServlet" method="post" class="formulario" id="formRegistroCliente">
          <h2>Crear Cuenta</h2>

          <% 
            String msg = request.getParameter("msg");
            if ("errorDni".equals(msg)) {
          %><p class="error-msg">El DNI ya está registrado.</p><%
            } else if ("errorTelefono".equals(msg)) {
          %><p class="error-msg">El teléfono ya está registrado.</p><%
            } else if ("errorCorreo".equals(msg)) {
          %><p class="error-msg">El correo ya está registrado.</p><%
            } else if ("errorPassword".equals(msg)) {
          %><p class="error-msg">Las contraseñas no coinciden.</p><%
            } else if ("error".equals(msg)) {
          %><p class="error-msg">Ocurrió un error al registrar. Inténtalo nuevamente.</p><%
            } else if ("exito".equals(msg)) {
          %><p class="success-msg">¡Registro exitoso! Ahora puedes iniciar sesión.</p><%
            }
          %>

          <!-- Nombre y Apellidos -->
          <div class="fila-doble">
            <div class="campo-con-icono">
              <label for="nombre">Nombre</label>
              <input type="text" id="nombre" name="nombre" required pattern="[A-Za-zÁÉÍÓÚáéíóúñÑ ]+" title="Solo letras y espacios">
            </div>
            <div class="campo-con-icono">
              <label for="apellidos">Apellidos</label>
              <input type="text" id="apellidos" name="apellidos" required pattern="[A-Za-zÁÉÍÓÚáéíóúñÑ ]+" title="Solo letras y espacios">
            </div>
          </div>

          <!-- DNI y Teléfono -->
          <div class="fila-doble">
            <div class="campo-con-icono">
              <label for="dni">DNI</label>
              <input type="text" id="dni" name="dni" required pattern="[0-9]{8}" maxlength="8" title="Debe contener exactamente 8 dígitos numéricos">
            </div>
            <div class="campo-con-icono">
              <label for="telefono">Teléfono</label>
              <input type="text" id="telefono" name="telefono" required pattern="[0-9]{9}" maxlength="9" title="Debe contener exactamente 9 dígitos numéricos">
            </div>
          </div>

          <!-- Correo -->
          <label for="correo">Correo Electrónico</label>
          <div class="campo-con-icono">
            <i class="fa-solid fa-envelope icono-izquierdo"></i>
            <input type="email" id="correo" name="correo" required>
          </div>

          <!-- Contraseña y Confirmar Contraseña -->
          <div class="fila-doble">
            <div class="campo-con-icono">
              <label for="password">Contraseña</label>
              <i class="fa-solid fa-lock icono-izquierdo"></i>
              <input type="password" id="password" name="password" required minlength="8">
              <i class="fa-solid fa-eye-slash toggle-password" onclick="togglePassword('password', this)"></i>
            </div>

            <div class="campo-con-icono">
              <label for="confirmar">Confirmar Contraseña</label>
              <i class="fa-solid fa-lock icono-izquierdo"></i>
              <input type="password" id="confirmar" name="confirmar" required minlength="8">
              <i class="fa-solid fa-eye-slash toggle-password" onclick="togglePassword('confirmar', this)"></i>
            </div>
          </div>

          <button type="submit">Registrarse</button>

          <p class="link-login">
            ¿Ya tienes una cuenta? <a href="login.jsp">Inicia sesión</a>
          </p>
        </form>
      </section>
    </main>

    <jsp:include page="footer.jsp" />
  </div>

  <script src="WebContent/js/web/validarRegistroCliente.js"></script>
</body>
</html>
