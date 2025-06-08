<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, modelo.Categoria, dao.CategoriaDAO" %>
<%
    CategoriaDAO categoriaDAO = new CategoriaDAO();
    List<Categoria> categoriasActivas = categoriaDAO.listarCategoriasActivas();
%>

<!-- Modal de Registro de Producto -->
<div id="modalRegistroProducto" class="modal">
    <div class="modal-content">
        <span class="close" id="cerrarModalProducto">&times;</span>
        <h2>Registrar Nuevo Producto</h2>

        <!-- Formulario con enctype para subir archivos -->
        <form action="AgregarProductoServlet" method="post" enctype="multipart/form-data">
            <!-- Nombre -->
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre" id="nombre" required>

            <!-- Descripción -->
            <label for="descripcion">Descripción:</label>
            <textarea name="descripcion" id="descripcion" rows="3" required></textarea>

            <!-- Precio -->
            <label for="precio">Precio:</label>
            <input type="number" name="precio" id="precio" min="0" step="0.01" required>

            <!-- Cantidad -->
            <label for="cantidad">Cantidad:</label>
            <input type="number" name="cantidad" id="cantidad" min="0" required>

            <!-- Categoría -->
            <label for="idCategoria">Categoría:</label>
            <select name="idCategoria" id="idCategoria" required>
                <option value="">Seleccione una categoría</option>
                <% for (Categoria c : categoriasActivas) { %>
                    <option value="<%= c.getIdCategoria() %>"><%= c.getNombre() %></option>
                <% } %>
            </select>

            <!-- Imagen -->
            <label for="imagen">Imagen:</label>
            <input type="file" name="imagen" id="imagen" accept="image/*" required>

            <!-- Estado oculto (siempre ACTIVO = 1) -->
            <input type="hidden" name="estado" value="1">

            <!-- Botones -->
            <div class="botones">
                <button type="submit" class="btn-guardar">Guardar</button>
                <button type="button" class="btn-cancelar" id="cancelarRegistroProducto">Cancelar</button>
            </div>
        </form>
    </div>
</div>
