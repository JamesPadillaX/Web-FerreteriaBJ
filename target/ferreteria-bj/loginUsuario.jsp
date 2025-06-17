<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login de Usuario - FerreteríaBJ</title>
    <link rel="stylesheet" href="WebContent/css/panel/loginUsuario.css"> 
</head>
<body>
    <div class="login-container">
        <form action="LoginUsuarioServlet" method="post" class="login-form">
            <h2>Iniciar Sesión</h2>

            <label for="username">Usuario</label>
            <input type="text" name="username" id="username" required>

            <label for="password">Contraseña</label>
            <input type="password" name="password" id="password" required>


            <%
            String error = (String) session.getAttribute("errorLogin");
            if (error != null) {

                %>
                <div class="error-msg"><%= error %></div>
                <%
                session.removeAttribute("errorLogin");
            }
            %>


            <button type="submit">Ingresar</button>
        </form>
    </div>
    <script src="WebContent/js/panel/loginUsuario.js"></script>
</body>
</html>
