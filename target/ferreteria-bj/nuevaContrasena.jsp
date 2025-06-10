<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Actualizar Contraseña - Ferretería BJ</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <link rel="stylesheet" href="WebContent/css/web/login.css">
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

    <main>
      <form action="ActualizarContrasenaServlet" method="post" class="formulario">
        <h2>Actualizar Contraseña</h2>

        <label for="nuevaPassword">Nueva Contraseña:</label>
        <div class="input-contrasena">
          <input type="password" id="nuevaPassword" name="nuevaPassword" required>
          <i class="fa-solid fa-eye-slash toggle-password" title="Mostrar/Ocultar contraseña"></i>
        </div>


        <button type="submit">Guardar Contraseña</button>

        <%
          HttpSession sesion = request.getSession(false);
          if (sesion != null) {
              String mensajeExito = (String) sesion.getAttribute("mensajeExito");
              String mensajeError = (String) sesion.getAttribute("mensajeError");

              if (mensajeExito != null) {
        %>
                <p class="exito"><%= mensajeExito %></p>
        <%
                sesion.removeAttribute("mensajeExito");
              }

              if (mensajeError != null) {
        %>
                <p class="error"><%= mensajeError %></p>
        <%
                sesion.removeAttribute("mensajeError");
              }
          }
        %>

        <p class="volver-login">
          <a href="login.jsp">Volver al inicio de sesión</a>
        </p>
      </form>
    </main>
    <jsp:include page="footer.jsp" />
  </div>
  <script src="WebContent/js/web/login.js"></script>
</body>
</html>
