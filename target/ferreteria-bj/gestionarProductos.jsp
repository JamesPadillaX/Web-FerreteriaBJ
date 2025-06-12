<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, modelo.Categoria, dao.CategoriaDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // Verificar sesión (ajusta según tu lógica)
    Object usuario = session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("loginUsuario.jsp");
        return;
    }

    // Obtener lista de categorías activas para el filtro
    CategoriaDAO categoriaDAO = new CategoriaDAO();
    List<Categoria> listaCategoria = categoriaDAO.listarCategoriasActivas();
    request.setAttribute("categorias", listaCategoria);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Gestionar Productos</title>

    <!-- FontAwesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

    <!-- Tus archivos CSS -->
    <link rel="stylesheet" href="WebContent/css/panel/panelPrincipal.css" />
    <link rel="stylesheet" href="WebContent/css/panel/gestionarProductos.css" />
    <link rel="stylesheet" href="WebContent/css/panel/modalEditarProducto.css" />
</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <jsp:include page="WebContent/componentes/sidebar.jsp" />

        <div class="content">
            <h1>Gestión de Productos</h1>

            <div class="export-buttons" style="margin-bottom: 15px;">
                <a href="ExportarProductosPdfServlet?idCategoria=${param.idCategoria}" 
                class="btn-exportar btn-pdf" style="margin-right: 10px;">
                
                <i class="fas fa-file-pdf"></i> Exportar PDF
            </a>
            </div>
            <button id="btnAbrirModalProducto" class="btn-agregar">
                <i class="fas fa-plus"></i> Agregar Nuevo Producto
            </button>


            <div class="filtros-container">
                <form method="get" action="ListarProductosServlet" class="form-filtro">
                    <div class="filtro-item">
                        <label for="idCategoria">Categoría:</label>
                        <select name="idCategoria" id="idCategoria">
                            <option value="">-- Todas --</option>
                            <c:forEach var="cat" items="${categorias}">
                            <option value="${cat.idCategoria}"
                            <c:if test="${param.idCategoria != null && param.idCategoria == cat.idCategoria.toString()}">selected</c:if>>
                            ${cat.nombre}
                        </option>
                    </c:forEach>
                </select>
            </div>
    </div>


            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Imagen</th>
                            <th>Categoría</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="producto" items="${productos}">
                            <tr>
                                <td>${producto.idProducto}</td>
                                <td>
                                    <c:if test="${not empty producto.imagen}">
                                        <img src="${pageContext.request.contextPath}/${producto.imagen}" 
                                             alt="Imagen ${producto.nombre}" width="100" />
                                    </c:if>
                                    <c:if test="${empty producto.imagen}">
                                        Sin imagen
                                    </c:if>
                                </td>
                                <td>${producto.categoria}</td>
                                <td>${producto.nombre}</td>
                                <td>${producto.descripcion}</td>
                                <td>S/${producto.precio}</td>
                                <td>${producto.cantidad}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${producto.estado == 1}">ACTIVO</c:when>
                                        <c:when test="${producto.estado == 0}">INACTIVO</c:when>
                                        <c:otherwise>Desconocido</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <button 
                                        type="button"
                                        class="action-btn btnEditarProducto"
                                        data-id="${producto.idProducto}"
                                        data-nombre="${producto.nombre}"
                                        data-descripcion="${producto.descripcion}"
                                        data-precio="${producto.precio}"
                                        data-cantidad="${producto.cantidad}"
                                        data-idcategoria="${producto.idCategoria}"
                                        data-estado="${producto.estado}"
                                        data-imagen="${producto.imagen}"
                                    >
                                        <i class="fas fa-edit"></i> Editar
                                    </button>

                                    <form action="EliminarProductoServlet" method="get" style="display:inline;" class="form-eliminar">
                                        <input type="hidden" name="idProducto" value="${producto.idProducto}" />
                                        <button class="action-btn delete" type="submit">      
                                            <i class="fas fa-trash-alt"></i> Eliminar
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Mensajes de alerta -->
    <c:if test="${param.msg == 'exito'}">
        <jsp:include page="WebContent/componentes/alerta.jsp" />
    </c:if>
    <c:if test="${param.msg == 'eliminado'}">
        <jsp:include page="WebContent/componentes/alertaEliminado.jsp" />
    </c:if>
    <c:if test="${param.msg == 'editado'}">
        <jsp:include page="WebContent/componentes/alertaEditado.jsp" />
    </c:if>

    <!-- Modales para agregar y editar productos -->
    <jsp:include page="WebContent/componentes/modalRegistroProducto.jsp" />
    <jsp:include page="WebContent/componentes/modalEditarProducto.jsp" />

    <!-- Scripts JS -->
    <script src="WebContent/js/panel/modalAgregarProduto.js"></script>
    <script src="WebContent/js/panel/eliminarProducto.js"></script>
    <script src="WebContent/js/panel/modalEditarProducto.js"></script>

<script>
    document.getElementById("idCategoria").addEventListener("change", function () {
        this.form.submit();
    });
</script>

</body>
</html>
