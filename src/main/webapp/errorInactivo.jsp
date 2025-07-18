<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="refresh" content="3;url=loginUsuario.jsp">
    <title>Cuenta Inactiva</title>
    <link rel="stylesheet" href="WebContent/css/panel/errroInactivo.css">
</head>
<body class="body-error-inactivo">
    <div class="error-container">
        <div class="error-icon">⚠️</div>
        <h2>Cuenta Inactiva</h2>
        <p>Tu cuenta se encuentra inactiva.<br> Por favor, contacta con el administrador del sistema.</p>
        <p style="font-size: 14px; color: gray; margin-top: 10px;">
            Serás redirigido automáticamente al inicio de sesión...
        </p>
        <a href="loginUsuario.jsp" class="btn-volver">Volver al Login</a>
    </div>
</body>
</html>
