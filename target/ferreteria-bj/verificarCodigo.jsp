<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Verificar Código - Ferretería BJ</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="WebContent/css/web/login.css">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

    <main>
      <form action="VerificarCodigoServlet" method="post" class="formulario">
        <h2>● ● ● ●</h2>

        <label for="codigo">Código recibido:</label>
        <input type="text" id="codigo" name="codigo" required maxlength="6" pattern="\d{4}" title="Debe ingresar 4 números">

        <button type="submit">Verificar</button>

        <% 
          String error = request.getParameter("error");
          if ("codigoInvalido".equals(error)) { 
        %>
          <p class="error">El código ingresado no es válido.</p>
        <% 
          } 
        %>
      </form>
    </main>

    <jsp:include page="footer.jsp" />
  </div>
</body>
</html>
