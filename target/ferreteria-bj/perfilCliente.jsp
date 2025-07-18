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

    <style>
        /* MODALES DEL PERFIL */

        .modal-perfil {
            display: none;
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background-color: rgba(0,0,0,0.6);
            justify-content: center;
            align-items: center;
            z-index: 2000;
            padding: 15px;
            box-sizing: border-box;
        }

        .contenedorCliente {
            background: white;
            padding: 25px 20px;
            border-radius: 15px;
            width: 100%;
            max-width: 400px;
            text-align: center;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
            position: relative;
            animation: fadeIn 0.3s ease;
            box-sizing: border-box;
        }

        .modal-cerrar {
            position: absolute;
            top: 10px;
            right: 15px;
            background: none;
            border: none;
            font-size: 22px;
            cursor: pointer;
            color: #666;
        }

        .modal-cerrar:hover {
            color: black;
        }

        .input-codigo {
            width: 100%;
            padding: 14px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 10px;
            margin-bottom: 18px;
            box-sizing: border-box;
        }

        .boton-accion {
            background-color: #0B1D51;
            color: white;
            font-size: 16px;
            padding: 12px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            width: 100%;
            box-sizing: border-box;
            font-weight: bold;
            letter-spacing: 0.5px;
            transition: background-color 0.3s ease;
        }

        .boton-accion:hover {
            background-color: #091440;
        }

        .mensaje-error {
            color: red;
            font-weight: bold;
            font-size: 14px;
            margin-bottom: 10px;
            display: block;
            text-align: center;
        }

        @keyframes fadeIn {
            from {opacity: 0; transform: scale(0.95);}
            to {opacity: 1; transform: scale(1);}
        }

        @media (max-width: 480px) {
            .contenedorCliente {
                padding: 20px 15px;
                font-size: 15px;
            }

            .input-codigo {
                font-size: 15px;
                padding: 12px;
            }

            .boton-accion {
                font-size: 15px;
                padding: 10px;
            }

            .modal-cerrar {
                font-size: 20px;
                top: 8px;
                right: 10px;
            }
        }
    </style>
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

                <form action="EnviarCodigoCambioPasswordServlet" method="post" style="margin-top: 20px;">
                    <input type="hidden" name="correo" value="<%= cliente.getCorreo() %>">
                    <button type="submit" class="boton-accion">
                        Cambiar Contraseña
                    </button>
                </form>
            </div>
        </div>
    </main>
</div>

<jsp:include page="footer.jsp" />

<!-- Modal Código -->
<div id="modalVerificarCodigo" class="modal-perfil">
    <div class="contenedorCliente">
        <button class="modal-cerrar" onclick="document.getElementById('modalVerificarCodigo').style.display='none'">✖</button>
        <h3>🔐 Verificación de Código</h3>
        <p>Ingresa el código enviado a tu correo:</p>

        <% if ("true".equals(request.getParameter("codigoError"))) { %>
            <p class="mensaje-error">⚠️ Código incorrecto. Intenta nuevamente.</p>
        <% } %>

        <form action="VerificarCodigoCambioPasswordServlet" method="post">
            <input type="text" name="codigoIngresado" required maxlength="6"
                   pattern="[0-9]{6}" inputmode="numeric"
                   placeholder="Código de 6 dígitos"
                   class="input-codigo">
            <button type="submit" class="boton-accion">Verificar Código</button>
        </form>
    </div>
</div>

<!-- Mostrar modal de código si corresponde -->
<% if ("true".equals(request.getParameter("codigoEnviado")) || "true".equals(request.getParameter("codigoError"))) { %>
<script>
    window.onload = () => document.getElementById('modalVerificarCodigo').style.display = 'flex';
</script>
<% } %>

<!-- Modal Nueva Password (separado completamente) -->
<div id="modalNuevaPassword" class="modal-perfil">
    <div class="contenedorCliente">
        <button class="modal-cerrar" onclick="document.getElementById('modalNuevaPassword').style.display='none'">✖</button>
        <h3>🔒 Establecer Nueva Contraseña</h3>
        <form action="ActualizarPassword" method="post" onsubmit="return validarPassword();">
            <input type="password" id="nuevaPassword" name="nuevaPassword" required
                   placeholder="Mínimo 8 caracteres y un número"
                   class="input-codigo">
            <small id="mensajePassword" class="mensaje-error" style="display:none;">
                La contraseña debe tener mínimo 8 caracteres, contener al menos un número y no usar caracteres especiales.
            </small>
            <button type="submit" class="boton-accion">
                Guardar Contraseña
            </button>
        </form>
    </div>
</div>

<!-- Mostrar modal nueva password si corresponde -->
<% if ("true".equals(request.getParameter("codigoVerificado"))) { %>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        document.getElementById('modalNuevaPassword').style.display = 'flex';
    });
</script>
<% } %>

</body>
</html>
