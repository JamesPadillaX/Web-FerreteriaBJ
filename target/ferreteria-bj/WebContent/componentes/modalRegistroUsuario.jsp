<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, modelo.Rol, dao.RolDAO" %>
<%
    RolDAO rolDAO = new RolDAO();
    List<Rol> listaRoles = rolDAO.listarRolesActivos();
%>

<div id="modalRegistroUsuario" class="modal">
    <div class="modal-content">
        <span class="close" id="cerrarModalRegistro">&times;</span>
        <h2>Registrar Nuevo Usuario</h2>

        <form action="AgregarUsuarioServlet" method="post" id="formRegistroUsuario">
            <input type="hidden" name="idUsuario" value="">

            <label>Nombre:</label>
            <input type="text" name="nombre" required>

            <label>Apellidos:</label>
            <input type="text" name="apellidos" required>

            <label>DNI:</label>
            <input type="text" name="dni" required 
                   pattern="\d{8}" 
                   minlength="8" maxlength="8" 
                   inputmode="numeric"
                   title="Debe contener exactamente 8 números">

            <label>Teléfono:</label>
            <input type="text" name="telefono" required 
                   pattern="\d{9}" 
                   minlength="9" maxlength="9" 
                   inputmode="numeric"
                   title="Debe contener exactamente 9 números">

            <label>Usuario:</label>
            <input type="text" name="username" required>

<label>Contraseña:</label>
<input type="password" name="password" id="passwordInput" required
       pattern="^(?=.*\d)[A-Za-z\d]{8,}$"
       title="Mínimo 8 caracteres, solo letras y números, y al menos un número">


            <label>Rol:</label>
            <select name="idRol" required>
                <option value="">Seleccione un rol</option>
                <% for (Rol r : listaRoles) { %>
                    <option value="<%= r.getIdRol() %>"><%= r.getNombre() %></option>
                <% } %>
            </select>

            <label>Estado:</label>
            <select name="estado">
                <option value="1">ACTIVO</option>
                <option value="0">INACTIVO</option>
            </select>

            <div class="botones">
                <button type="submit" class="btn-guardar">Guardar</button>
                <button type="button" class="btn-cancelar" id="cancelarRegistro">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<!-- JavaScript de validación -->
<script>
    // Limita a solo números con longitud máxima
    function limitarNumeros(input, maxLength) {
        input.addEventListener('keypress', function (e) {
            const key = e.key;
            if (!/^\d$/.test(key) || input.value.length >= maxLength) {
                e.preventDefault();
            }
        });

        input.addEventListener('input', function () {
            input.value = input.value.replace(/\D/g, '');
            if (input.value.length > maxLength) {
                input.value = input.value.slice(0, maxLength);
            }
        });
    }

    // Limita a solo letras y espacios
    function limitarLetras(input) {
        input.addEventListener('keypress', function (e) {
            const key = e.key;
            if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]$/.test(key)) {
                e.preventDefault();
            }
        });

        input.addEventListener('input', function () {
            input.value = input.value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ\s]/g, '');
        });
    }

    document.addEventListener('DOMContentLoaded', function () {
        const dniInput = document.querySelector('input[name="dni"]');
        const telefonoInput = document.querySelector('input[name="telefono"]');
        const nombreInput = document.querySelector('input[name="nombre"]');
        const apellidosInput = document.querySelector('input[name="apellidos"]');

        if (dniInput) limitarNumeros(dniInput, 8);
        if (telefonoInput) limitarNumeros(telefonoInput, 9);
        if (nombreInput) limitarLetras(nombreInput);
        if (apellidosInput) limitarLetras(apellidosInput);
    });
</script>
<script>
    const passwordInput = document.getElementById('passwordInput');

    passwordInput.addEventListener('input', function () {
        // Elimina cualquier carácter que no sea letra o número
        this.value = this.value.replace(/[^A-Za-z0-9]/g, '');
    });

    const form = document.getElementById('formRegistroUsuario');
    form.addEventListener('submit', function (e) {
        const value = passwordInput.value;
        const regex = /^(?=.*\d)[A-Za-z\d]{8,}$/;

        if (!regex.test(value)) {
            alert('La contraseña debe tener mínimo 8 caracteres, solo letras y números, y al menos un número.');
            e.preventDefault();
        }
    });
</script>
