<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="modalRegistroCategoria" class="modal">
    <div class="modal-content">
        <span class="close-btn" id="cerrarModalRegistroCategoria">&times;</span>
        <h2>Registrar Nueva Categoría</h2>

        <form id="formRegistroCategoria" action="AgregarCategoriaServlet" method="post">
            <label for="nombreCategoria">Nombre de la Categoría:</label>
            <input type="text" id="nombreCategoria" name="nombre" placeholder="Ejemplo: Herramientas" required />

            <div class="modal-buttons">
                <button type="submit" class="btn-guardar">Guardar</button>
                <button type="button" class="btn-cancelar" id="btnCancelarRegistroCategoria">Cancelar</button>
            </div>
        </form>
    </div>
</div>
