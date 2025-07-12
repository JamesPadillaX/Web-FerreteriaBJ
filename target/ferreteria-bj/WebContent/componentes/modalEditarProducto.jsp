<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, modelo.Categoria, dao.CategoriaDAO" %>
<%
    CategoriaDAO categoriaDAO = new CategoriaDAO();
    List<Categoria> categoriasActivas = categoriaDAO.listarCategoriasActivas();
%>

<div id="modalEditarProducto" class="modal">
    <div class="modal-content">
        <span class="close" id="cerrarModalEditarProducto">&times;</span>
        <h2>Editar Producto</h2>

        <form id="formEditarProducto" action="EditarProductoServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="idProducto" id="edit_idProducto">

            <label for="edit_nombre">Nombre:</label>
            <input type="text" name="nombre" id="edit_nombre" required>

            <label for="edit_descripcion">Descripción:</label>
            <textarea name="descripcion" id="edit_descripcion" rows="3" required></textarea>


            <label for="edit_precio">Precio:</label>
            <input type="number" name="precio" id="edit_precio" min="0" step="0.01" required>

            <!-- Cantidad -->
            <label for="edit_cantidad">Cantidad:</label>
            <input type="number" name="cantidad" id="edit_cantidad" min="0" required>

            <!-- Categoría -->
            <label for="edit_idCategoria">Categoría:</label>
            <select name="idCategoria" id="edit_idCategoria" required>
                <option value="">Seleccione una categoría</option>
                <% for (Categoria c : categoriasActivas) { %>
                    <option value="<%= c.getIdCategoria() %>"><%= c.getNombre() %></option>
                <% } %>
            </select>

            <!-- Imagen nueva -->
            <label for="edit_imagen">Imagen (opcional):</label>
            <input type="file" name="imagen" id="edit_imagen" accept="image/*">

            <!-- Imagen actual (valor oculto para backend) -->
            <input type="hidden" name="imagenActual" id="edit_imagenActual">

            <!-- Vista previa de la imagen actual -->
            <label>Imagen actual:</label><br>
            <img id="previewImagenActual" src="" alt="Imagen actual" style="max-width: 150px; margin-bottom: 10px;">

            <!-- Estado -->
            <label for="edit_estado">Estado:</label>
            <select name="estado" id="edit_estado" required>
                <option value="1">Activo</option>
                <option value="0">Inactivo</option>
            </select>

            <!-- ⬇️ NUEVO: Filtros actuales -->
            <input type="hidden" name="nombreFiltro" id="edit_nombreFiltro">
            <input type="hidden" name="idCategoriaFiltro" id="edit_idCategoriaFiltro">

            <!-- Botones -->
            <div class="botones">
                <button type="submit" class="btn-guardar">Actualizar</button>
                <button type="button" class="btn-cancelar" id="cancelarEditarProducto">Cancelar</button>
            </div>
        </form>
    </div>
</div>
