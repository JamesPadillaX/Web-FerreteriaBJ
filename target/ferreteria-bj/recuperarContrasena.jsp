<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Recuperar Contraseña - Ferretería BJ</title>
  <link rel="stylesheet" href="WebContent/css/web/styles.css">
  <link rel="stylesheet" href="WebContent/css/web/login.css">
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

    <main>
      <form action="EnviarCodigoServlet" method="post" class="formulario">
        <h2>Recuperar Contraseña</h2>

        <label for="correo">Correo Electrónico</label>
        <input type="email" id="correo" name="correo" required>

        <button type="submit">Enviar código</button>

        <% String error = request.getParameter("error"); %>
        <% if ("noExiste".equals(error)) { %>
          <p class="error">El correo ingresado no está registrado.</p>
        <% } else if ("envio".equals(error)) { %>
          <p class="error">No se pudo enviar el código. Intenta más tarde.</p>
        <% } %>

        <% if ("enviado".equals(request.getParameter("status"))) { %>
          <p class="exito">Código enviado. Revisa tu correo electrónico.</p>
        <% } %>

        <p class="volver-login">
          <a href="login.jsp">Volver al inicio de sesión</a>
        </p>
      </form>
    </main>

    <jsp:include page="footer.jsp" />
  </div>
</body>
</html>
