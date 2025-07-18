<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, modelo.Rol, dao.RolDAO" %>
<%
    RolDAO rolDAO = new RolDAO();
    List<Rol> listaRoles = rolDAO.listarRolesActivos();
%>

<div id="modalEditarUsuario" class="modal">
    <div class="modal-content">
        <span class="close" id="cerrarModalEditar">&times;</span>
        <h2>Editar Usuario</h2>

        <form action="EditarUsuarioServlet" method="post">

            <input type="hidden" name="idUsuario" id="editarIdUsuario" value="">


            <label>Nombre:</label>
            <input type="text" name="nombre" id="editarNombre" required 
                   pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" 
                   title="Solo letras" 
                   maxlength="50">


            <label>Apellidos:</label>
            <input type="text" name="apellidos" id="editarApellidos" required 
                   pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" 
                   title="Solo letras" 
                   maxlength="50">


            <label>DNI:</label>
            <input type="text" name="dni" id="editarDni" required 
                   pattern="\d{8}" 
                   maxlength="8" 
                   title="Debe tener exactamente 8 dígitos" 
                   oninput="this.value=this.value.replace(/[^0-9]/g,'');">


            <label>Teléfono:</label>
            <input type="text" name="telefono" id="editarTelefono" required 
                   pattern="\d{9}" 
                   maxlength="9" 
                   title="Debe tener exactamente 9 dígitos" 
                   oninput="this.value=this.value.replace(/[^0-9]/g,'');">


            <label>Usuario:</label>
            <input type="text" name="username" id="editarUsername" required>


<label>Contraseña:</label>
<input type="password" name="password" id="editarPassword" required>
<small id="mensajePassword" style="color: red; display: none;">
  La contraseña debe tener mínimo 8 caracteres, solo letras y números, y al menos un número.
</small>


            <label>Rol:</label>
            <select name="idRol" id="editarIdRol" required>
                <option value="">Seleccione un rol</option>
                <% for (Rol r : listaRoles) { %>
                    <option value="<%= r.getIdRol() %>"><%= r.getNombre() %></option>
                <% } %>
            </select>

  
            <label>Estado:</label>
            <select name="estado" id="editarEstado">
                <option value="1">ACTIVO</option>
                <option value="0">INACTIVO</option>
            </select>

            <div class="botones">
                <button type="submit" class="btn-guardar">Guardar Cambios</button>
                <button type="button" class="btn-cancelar" id="cancelarEditar">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<script>
  document.addEventListener("DOMContentLoaded", () => {
    const soloLetras = /[^a-zA-ZÁÉÍÓÚáéíóúÑñ\s]/g;

    const nombreInput = document.getElementById("editarNombre");
    const apellidosInput = document.getElementById("editarApellidos");

    nombreInput.addEventListener("input", () => {
      nombreInput.value = nombreInput.value.replace(soloLetras, "");
    });

    apellidosInput.addEventListener("input", () => {
      apellidosInput.value = apellidosInput.value.replace(soloLetras, "");
    });
  });
  document.addEventListener("DOMContentLoaded", () => {
    const passwordInput = document.getElementById("editarPassword");
    const formulario = document.querySelector('form');

    formulario.addEventListener("submit", (e) => {
        const valor = passwordInput.value;

        const soloLetrasNumeros = /^[A-Za-z0-9]+$/.test(valor);
        const minimoOcho = valor.length >= 8;
        const contieneNumero = /[0-9]/.test(valor);

        const esValida = soloLetrasNumeros && minimoOcho && contieneNumero;

        if (!esValida) {
            e.preventDefault();
        }
    });
});

</script>
