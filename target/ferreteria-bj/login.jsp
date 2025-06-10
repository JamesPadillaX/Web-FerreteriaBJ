<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Iniciar Sesión - Ferretería BJ</title>
  <link rel="stylesheet" href="WebContent/css/web/login.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

    <main>
      <form action="LoginServlet" method="post" class="formulario">
        <h2>Iniciar Sesión</h2>

        <label for="usuario">Correo Electrónico</label>
        <input type="email" id="usuario" name="correo"
          value="<%= request.getParameter("correo") != null ? request.getParameter("correo") : "" %>" required>

        <label for="contrasena">Contraseña</label>
        <div class="input-contrasena">
          <input type="password" id="contrasena" name="password" required>
          <i class="fa-solid fa-eye-slash toggle-password"></i>
        </div>


        <button type="submit">Ingresar</button>

        <p class="olvide-contrasena">
          <a href="recuperarContrasena.jsp">¿Olvidaste tu contraseña?</a>
        </p>
      </form>

      <%-- Mostrar alerta si hay error --%>
      <%
        String error = request.getParameter("error");
        if ("1".equals(error)) {
      %>
        <jsp:include page="WebContent/componentes/alertaInicioSeccion.jsp" />
      <%
        }
      %>
    </main>

    <jsp:include page="footer.jsp" />
  </div>


  <script src="WebContent/js/web/login.js"></script>
</body>
</html>
