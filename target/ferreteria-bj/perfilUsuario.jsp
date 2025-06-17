<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("loginUsuario.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Perfil Usuario - Ferretería BJ</title>
    <link rel="stylesheet" href="WebContent/css/panel/usuario.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
</head>
<body>
    <div class="container">
        <jsp:include page="WebContent/componentes/sidebar.jsp" />

        <div class="content-perfil">
            <div class="info-box">
                <h2><i class="fas fa-user-circle"></i> Bienvenido, <%= usuario.getNombre() + " " + usuario.getApellidos() %></h2>
                <p><strong>Rol:</strong> <%= usuario.getRol() != null ? usuario.getRol().getNombre() : "No definido" %></p>
                <p><strong>DNI:</strong> <%= usuario.getDni() %></p>
                <p><strong>Teléfono:</strong> <%= usuario.getTelefono() %></p>
            </div>
        </div>
    </div>
</body>
</html>
