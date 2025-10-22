<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Cliente" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro - Ferretería BJ</title>
    <link rel="stylesheet" href="WebContent/css/web/registro.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        .input-error {
            border: 2px solid red !important;
            background-color: #ffe5e5;
        }
        .error-msg {
            color: red;
            font-size: 14px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="contenedor-principal">

    <main>
        <section>
            <form action="RegistrarServlet" method="post" class="formulario" id="formRegistroCliente">
                <h2>Crear Cuenta</h2>

  <%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    String msg = (String) request.getAttribute("msg");
    if (msg == null) {
        msg = request.getParameter("msg");
    }

    String nombreVal = "", apellidosVal = "", dniVal = "", telefonoVal = "", correoVal = "";
    String dniClass = "", telefonoClass = "", correoClass = "";

    if (cliente != null) {
        nombreVal = cliente.getNombre() != null ? cliente.getNombre() : "";
        apellidosVal = cliente.getApellidos() != null ? cliente.getApellidos() : "";
        dniVal = cliente.getDni() != null ? cliente.getDni() : "";
        telefonoVal = cliente.getTelefono() != null ? cliente.getTelefono() : "";
        correoVal = cliente.getCorreo() != null ? cliente.getCorreo() : "";
    }

    if ("errorDni".equals(msg)) dniClass = "input-error";
    if ("errorTelefono".equals(msg)) telefonoClass = "input-error";
    if ("errorCorreo".equals(msg)) correoClass = "input-error";
%>


                <div class="fila-doble">
                    <div class="campo-con-icono">
                        <label for="nombre">Nombre</label>
                        <input type="text" id="nombre" name="nombre" required
                               pattern="[A-Za-zÁÉÍÓÚáéíóúñÑ ]+"
                               title="Solo letras y espacios"
                               value="<%= nombreVal %>">
                    </div>
                    <div class="campo-con-icono">
                        <label for="apellidos">Apellidos</label>
                        <input type="text" id="apellidos" name="apellidos" required
                               pattern="[A-Za-zÁÉÍÓÚáéíóúñÑ ]+"
                               title="Solo letras y espacios"
                               value="<%= apellidosVal %>">
                    </div>
                </div>

                <div class="fila-doble">
                    <div class="campo-con-icono">
                        <label for="dni">DNI</label>
                        <input type="text" id="dni" name="dni" required maxlength="8"
                               pattern="^[0-9]{8}$"
                               title="Debe contener exactamente 8 dígitos numéricos."
                               value="<%= dniVal %>"
                               class="<%= dniClass %>">
                    </div>
                    <div class="campo-con-icono">
                        <label for="telefono">Teléfono</label>
                        <input type="text" id="telefono" name="telefono" required maxlength="9"
                               pattern="^9[0-9]{8}$"
                               title="Debe iniciar con 9 y tener exactamente 9 dígitos numéricos."
                               value="<%= telefonoVal %>"
                               class="<%= telefonoClass %>">
                    </div>
                </div>

                <label for="correo">Correo Electrónico</label>
                <div class="campo-con-icono">
                    <i class="fa-solid fa-envelope icono-izquierdo"></i>
                    <input type="email" id="correo" name="correo" required
                           value="<%= correoVal %>"
                           class="<%= correoClass %>">
                </div>

                <div class="fila-doble">
                    <div class="campo-con-icono">
                        <label for="password">Contraseña</label>
                        <i class="fa-solid fa-lock icono-izquierdo"></i>
                        <input type="password" id="password" name="password" required
                               minlength="8"
                               pattern="^(?=.*[A-Za-z])(?=.*\d).{8,}$"
                               title="Debe tener mínimo 8 caracteres, al menos una letra y un número.">
                        <i class="fa-solid fa-eye-slash toggle-password"
                           onclick="togglePassword('password', this)"></i>
                    </div>
                    <div class="campo-con-icono">
                        <label for="confirmar">Confirmar Contraseña</label>
                        <i class="fa-solid fa-lock icono-izquierdo"></i>
                        <input type="password" id="confirmar" name="confirmar" required
                               minlength="8"
                               pattern="^(?=.*[A-Za-z])(?=.*\d).{8,}$"
                               title="Debe tener mínimo 8 caracteres, al menos una letra y un número.">
                        <i class="fa-solid fa-eye-slash toggle-password"
                           onclick="togglePassword('confirmar', this)"></i>
                    </div>
                </div>

                <button type="submit">Registrarse</button>

                <p class="link-login">
                    ¿Ya tienes una cuenta? <a href="login.jsp">Inicia sesión</a>
                </p>
            </form>
        </section>
    </main>

    <%@ include file="footer.jsp" %>
</div>
<script src="WebContent/js/web/validarRegistroCliente.js"></script>
</body>
</html>
