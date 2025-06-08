<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- modalRegistroRol.jsp -->
<div id="modalRegistroRol" class="modal">
    <div class="modal-content">
        <span class="close-btn" id="cerrarModalRegistro">&times;</span>
        <h2>Registro Nuevo Rol</h2>

        <form id="formRegistroRol" action="AgregarRolServlet" method="post">
            <label for="nombre">Nombre del Rol:</label>
            <input type="text" id="nombre" name="nombre" placeholder="Ejemplo: ADMIN" required />

            <div class="modal-buttons">
                <button type="submit" class="btn-guardar">Guardar</button>
                <button type="button" class="btn-cancelar" id="btnCancelarRegistroRol">Cancelar</button>
            </div>
        </form>
    </div>
</div>
