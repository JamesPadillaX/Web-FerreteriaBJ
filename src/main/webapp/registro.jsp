<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Registro - Ferretería BJ</title>
  <link rel="stylesheet" href="WebContent/css/web/styles.css">
  <link rel="stylesheet" href="WebContent/css/web/registro.css">
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

    <main>
      <section>
        <form action="RegistrarServlet" method="post" class="formulario">
          <h2>Crear Cuenta</h2>

          <% 
            String msg = request.getParameter("msg");
            if ("errorDni".equals(msg)) {
          %>
              <p style="color: red;">El DNI ya está registrado.</p>
          <% } else if ("errorTelefono".equals(msg)) { %>
              <p style="color: red;">El teléfono ya está registrado.</p>
          <% } else if ("errorCorreo".equals(msg)) { %>
              <p style="color: red;">El correo ya está registrado.</p>
          <% } else if ("errorPassword".equals(msg)) { %>
              <p style="color: red;">Las contraseñas no coinciden.</p>
          <% } else if ("error".equals(msg)) { %>
              <p style="color: red;">Ocurrió un error al registrar. Inténtalo nuevamente.</p>
          <% } else if ("exito".equals(msg)) { %>
              <p style="color: green;">¡Registro exitoso! Ahora puedes iniciar sesión.</p>
          <% } %>

          <label for="nombre">Nombre</label>
          <input type="text" id="nombre" name="nombre" required>

          <label for="apellidos">Apellidos</label>
          <input type="text" id="apellidos" name="apellidos" required>

          <label for="dni">DNI</label>
          <input type="text" id="dni" name="dni" required pattern="[0-9]{8,15}" title="Debe contener entre 8 y 15 dígitos">

          <label for="telefono">Teléfono</label>
          <input type="tel" id="telefono" name="telefono" required pattern="[0-9+ -]{7,20}" title="Número válido entre 7 y 20 caracteres">

          <label for="correo">Correo Electrónico</label>
          <input type="email" id="correo" name="correo" required>

          <label for="password">Contraseña</label>
          <input type="password" id="password" name="password" required minlength="6">

          <label for="confirmar">Confirmar Contraseña</label>
          <input type="password" id="confirmar" name="confirmar" required minlength="6">

          <button type="submit">Registrarse</button>

          <p style="text-align: center; margin-top: 1rem;">
            ¿Ya tienes una cuenta? <a href="login.jsp">Inicia sesión</a>
          </p>
        </form>
      </section>
    </main>

    <jsp:include page="footer.jsp" />
  </div>
</body>
</html>
