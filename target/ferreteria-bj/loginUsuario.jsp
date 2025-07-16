<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login de Usuario - FerreteríaBJ</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="WebContent/css/panel/loginUsuario.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <div class="background-image"></div>

    <div class="login-box">
        <div class="logo-section">
            <img src="WebContent/images/logo.png" alt="Logo Ferretería">
        </div>

        <form action="LoginUsuarioServlet" method="post">
            <h2>Acceso al Sistema</h2>

            <div class="input-box">
                <i class="fas fa-user"></i>
                <input type="text" name="username" placeholder="Usuario"
                       value="<%= session.getAttribute("usernameGuardado") != null ? session.getAttribute("usernameGuardado") : "" %>"
                       required>
            </div>

            <div class="input-box">
                <i class="fas fa-lock"></i>
                <input type="password" name="password" placeholder="Contraseña" required>
            </div>

            <%
                String error = (String) session.getAttribute("errorLogin");
                if (error != null) {
            %>
                <div class="error-msg"><%= error %></div>
            <%
                    session.removeAttribute("errorLogin");
                }
                session.removeAttribute("usernameGuardado"); // Limpiar el username una vez mostrado
            %>

            <button type="submit">Ingresar</button>
        </form>
    </div>

    <script src="WebContent/js/panel/loginUsuario.js"></script>
</body>
</html>
