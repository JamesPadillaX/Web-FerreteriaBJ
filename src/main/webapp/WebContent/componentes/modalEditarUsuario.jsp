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
            <input type="text" name="nombre" id="editarNombre" required>

            <label>Apellidos:</label>
            <input type="text" name="apellidos" id="editarApellidos" required>

            <label>DNI:</label>
            <input type="text" name="dni" id="editarDni" required>

            <label>Teléfono:</label>
            <input type="text" name="telefono" id="editarTelefono" required>

            <label>Usuario:</label>
            <input type="text" name="username" id="editarUsername" required>

            <label>Contraseña:</label>
            <input type="password" name="password" id="editarPassword" required>

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
