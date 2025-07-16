<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Cliente" %>
<%
    Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");
    if (cliente == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Perfil del Cliente</title>
    <link rel="stylesheet" href="WebContent/css/web/perfil.css">
 
</head>
<body class="body-flex">
    <jsp:include page="header.jsp" />

    <div class="contenedor-principal">
        <main class="contenido">
            <div class="perfil-contenedor">
                <h2>DATOS PERSONALES</h2>
                <div class="perfil-info">
                    <p><strong>Nombre:</strong> <%= cliente.getNombre() %></p>
                    <p><strong>Apellidos:</strong> <%= cliente.getApellidos() %></p>
                    <p><strong>DNI:</strong> <%= cliente.getDni() %></p>
                    <p><strong>Teléfono:</strong> <%= cliente.getTelefono() %></p>
                    <p><strong>Correo:</strong> <%= cliente.getCorreo() %></p>
                </div>
            </div>
        </main>
    </div>

    <jsp:include page="footer.jsp" />
</body>

</html>
