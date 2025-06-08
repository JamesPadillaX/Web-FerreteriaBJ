<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="modalEditarCategoria" class="modal">
    <div class="modal-content">
        <span class="close-btn" id="cerrarModalEditarCategoria">&times;</span>
        <h2>Editar Categoría</h2>

        <form id="formEditarCategoria" action="EditarCategoriaServlet" method="post">
            <!-- ID oculto para saber qué categoría editar -->
            <input type="hidden" id="idCategoriaEditar" name="idCategoria" />

            <label for="nombreCategoriaEditar">Nombre de la Categoría:</label>
            <input type="text" id="nombreCategoriaEditar" name="nombre" required />

            <label for="estadoCategoriaEditar">Estado:</label>
            <select id="estadoCategoriaEditar" name="estado" required>
                <option value="1">ACTIVO</option>
                <option value="0">INACTIVO</option>
            </select>

            <div class="modal-buttons">
                <button type="submit" class="btn-guardar">Guardar Cambios</button>
                <button type="button" class="btn-cancelar" id="btnCancelarEditarCategoria">Cancelar</button>
            </div>
        </form>
    </div>
</div>
