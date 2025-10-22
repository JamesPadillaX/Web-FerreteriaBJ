<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Actualizar Contraseña - Ferretería BJ</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <link rel="stylesheet" href="WebContent/css/web/login.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
  <div class="contenedor-principal">
    <main>
     <form action="ActualizarContrasenaServlet" method="post" class="formulario">
  <h2>Actualizar Contraseña</h2>

  <div class="campo-con-icono">
    <label for="nuevaPassword">Nueva Contraseña:</label>
    <div class="input-con-icono input-contrasena">
      <i class="fa-solid fa-lock icono-izquierdo"></i>
      <input type="password"
       id="nuevaPassword"
       name="nuevaPassword"
       required
       pattern=".{8,}"
       title="La contraseña debe tener al menos 8 caracteres.">
      <i class="fa-solid fa-eye-slash toggle-password" onclick="togglePassword(this, 'nuevaPassword')"></i>
    </div>
  </div>

  <button type="submit">Guardar Contraseña</button>

  <% 
    HttpSession sesion = request.getSession(false);
    if (sesion != null) {
        String mensajeExito = (String) sesion.getAttribute("mensajeExito");
        String mensajeError = (String) sesion.getAttribute("mensajeError");

        if (mensajeExito != null) { %>
            <p class="exito"><%= mensajeExito %></p>
  <%        sesion.removeAttribute("mensajeExito");
        }
        if (mensajeError != null) { %>
            <p class="error"><%= mensajeError %></p>
  <%        sesion.removeAttribute("mensajeError");
        }
    }
  %>
</form>

    </main>
    <jsp:include page="footer.jsp" />
  </div>
  <script src="WebContent/js/web/login.js"></script>
</body>
</html>
