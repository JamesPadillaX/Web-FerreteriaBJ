<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- modalEditarRol.jsp -->
<div id="modalEditarRol" class="modal">
    <div class="modal-content">
        <span class="close" id="cerrarModalEditar">&times;</span>
        <h2>Editar Rol</h2>
        <form action="EditarRolServlet" method="post" id="formEditarRol">
            <input type="hidden" id="idRolEditar" name="idRol" />

            <div class="form-group">
                <label for="nombreEditar">Nombre del Rol:</label>
                <input type="text" id="nombreEditar" name="nombre" required maxlength="50" />
            </div>
            <div class="form-group">
                <label for="estadoEditar">Estado:</label>
                <select id="estadoEditar" name="estado" required>
                    <option value="1">ACTIVO</option>
                    <option value="0">INACTIVO</option>
                </select>
            </div>
            <div class="modal-buttons">
                <button type="submit" class="btn-guardar">Guardar Cambios</button>
                <button type="button" class="btn-cancelar" id="btnCancelarEditarRol">Cancelar</button>
            </div>
        </form>
    </div>
</div>
