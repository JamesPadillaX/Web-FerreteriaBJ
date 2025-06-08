<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Verificar Código - Ferretería BJ</title>
  <link rel="stylesheet" href="WebContent/css/web/styles.css">
  <link rel="stylesheet" href="WebContent/css/web/login.css">
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

    <main>
      <form action="VerificarCodigoServlet" method="post" class="formulario">
        <h2>Verificar Código</h2>

        <label for="codigo">Código recibido:</label>
        <input type="text" id="codigo" name="codigo" required>

        <button type="submit">Verificar</button>

        <% 
          String error = request.getParameter("error");
          if ("codigoInvalido".equals(error)) { 
        %>
          <p class="error">El código ingresado no es válido.</p>
        <% 
          } 
        %>

        <p class="volver-login">
          <a href="login.jsp">Volver al inicio de sesión</a>
        </p>
      </form>
    </main>

    <jsp:include page="footer.jsp" />
  </div>
</body>
</html>
