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
    <title>Agregar domicilio</title>
    <link rel="stylesheet" href="WebContent/css/web/formDomicilio.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="layout">
    <%@ include file="header.jsp" %>

    <main>
        <div class="contenedor-formulario">
            <h1>Registrar nuevo domicilio</h1>

            <form action="AgregarDomicilioServlet" method="post" class="formulario-domicilio">
                <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">

                <label>Calle:
                    <input type="text" name="calle" required placeholder="Ej. Av. Los Héroes">
                </label>

                <label>Número:
                    <input type="text" name="numero" required placeholder="Ej. 123">
                </label>

                <label>Referencia:
                    <input type="text" name="referencia" placeholder="Ej. Cerca al parque o a la tienda X">
                </label>

                <label>Distrito:
                    <input type="text" name="distrito" required placeholder="Ej. San Miguel">
                </label>

                <label>Provincia:
                    <input type="text" name="provincia" required placeholder="Ej. Lima">
                </label>

                <label>Departamento:
                    <input type="text" name="departamento" required placeholder="Ej. Lima">
                </label>

                <label class="checkbox">
                    <input type="checkbox" name="principal" value="1">
                    Marcar como domicilio principal
                </label>

                <div class="botones">
                    <button type="submit" class="btn-guardar">Guardar domicilio</button>
                    <a href="domicilio.jsp" class="btn-cancelar">Cancelar</a>
                </div>
            </form>
        </div>
    </main>

    <%@ include file="footer.jsp" %>
</div>
</body>
</html>
