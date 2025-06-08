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

        <form action="AgregarUsuarioServlet" method="post">
            <input type="hidden" name="idUsuario" value="">

            <label>Nombre:</label>
            <input type="text" name="nombre" required>

            <label>Apellidos:</label>
            <input type="text" name="apellidos" required>

            <label>DNI:</label>
            <input type="text" name="dni" required>

            <label>Teléfono:</label>
            <input type="text" name="telefono" required>

            <label>Usuario:</label>
            <input type="text" name="username" required>

            <label>Contraseña:</label>
            <input type="password" name="password" required>

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
