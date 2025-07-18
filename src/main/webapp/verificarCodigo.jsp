<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Verificar Código - Ferretería BJ</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="WebContent/css/web/login.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

    <main>
      <form action="VerificarCodigoServlet" method="post" class="formulario">
        <h2>● ● ● ●</h2>

        <div class="campo-con-icono">
          <label for="codigo">Código recibido:</label>
          <div class="input-con-icono">
            <i class="fa-solid fa-lock icono-izquierdo"></i>
            <input type="text" id="codigo" name="codigo" required maxlength="6" pattern="\d{6}"
                   title="Debe ingresar 6 números"
                   oninput="filtrarCodigo(this)">
          </div>
        </div>

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

  <script>
    function filtrarCodigo(input) {
      input.value = input.value.replace(/[^\d]/g, '').replace(/[<>]/g, '');
    }
  </script>
</body>
</html>
