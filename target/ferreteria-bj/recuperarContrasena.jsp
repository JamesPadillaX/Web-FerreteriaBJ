<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Recuperar Contraseña - Ferretería BJ</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <link rel="stylesheet" href="WebContent/css/web/login.css">
</head>
<body>
  <div class="contenedor-principal">
    <main>
      <form action="EnviarCodigoServlet" method="post" class="formulario">
        <h2>Recuperar Contraseña</h2>

        <div class="campo-con-icono">
          <label for="correo">Correo Electrónico</label>
          <div class="input-con-icono">
            <i class="fa-solid fa-envelope icono-izquierdo"></i>
            <input type="email" id="correo" name="correo"
            value="<%= request.getParameter("correo") != null ? request.getParameter("correo") : "" %>" required>
          </div>
        </div>
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

        <div class="volver-login">
          <a href="login.jsp">Volver al inicio de sesión</a>
        </div>
      </form>
    </main>
    <jsp:include page="footer.jsp" />
  </div>
</body>
</html>
