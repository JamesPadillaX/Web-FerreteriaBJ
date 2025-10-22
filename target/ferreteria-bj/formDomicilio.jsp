<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Cliente" %>
<%@ include file="header.jsp" %>
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


    <main>
        <div class="contenedor-formulario">
            <h1>Registrar nuevo domicilio</h1>

            <form action="AgregarDomicilioServlet" method="post" class="formulario-domicilio" autocomplete="off">
                <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">

                <label for="calle">Calle:
                    <input type="text" name="calle" id="calle" required
                           pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{1,50}$"
                           title="Solo letras y espacios."
                           oninput="permitirSoloLetras(this)">
                </label>

                <label for="numero">Número:
                    <input type="text" name="numero" id="numero" required
                           pattern="^[A-Za-z0-9 ]{1,10}$"
                           title="Solo letras, números y espacios."
                           oninput="permitirLetrasNumeros(this)">
                </label>

                <label for="referencia">Referencia:
                    <input type="text" name="referencia" id="referencia"
                           pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{0,100}$"
                           title="Solo letras y espacios."
                           oninput="permitirSoloLetras(this)">
                </label>

                <label for="distrito">Distrito:
                    <input type="text" name="distrito" id="distrito" required
                           pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{1,30}$"
                           title="Solo letras y espacios."
                           oninput="permitirSoloLetras(this)">
                </label>

                <label for="provincia">Provincia:
                    <input type="text" name="provincia" id="provincia" required
                           pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{1,30}$"
                           title="Solo letras y espacios."
                           oninput="permitirSoloLetras(this)">
                </label>

                <label for="departamento">Departamento:
                    <input type="text" name="departamento" id="departamento" required
                           pattern="^[A-Za-zÁÉÍÓÚÑáéíóúñ ]{1,30}$"
                           title="Solo letras y espacios."
                           oninput="permitirSoloLetras(this)">
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

<script>
    function permitirSoloLetras(input) {
        input.value = input.value.replace(/[^A-Za-zÁÉÍÓÚÑáéíóúñ ]/g, '');
    }

    function permitirLetrasNumeros(input) {
        input.value = input.value.replace(/[^A-Za-z0-9 ]/g, '');
    }
</script>

</body>
</html>
