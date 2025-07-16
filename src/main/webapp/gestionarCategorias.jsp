<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestionar Categorías</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="WebContent/css/panel/gestionarCategorias.css">
    <link rel="stylesheet" href="WebContent/css/panel/modalRegistroCategoria.css">
    <link rel="stylesheet" href="WebContent/css/panel/modalEditarCategoria.css">
</head>

<body>
<div class="container">
    <jsp:include page="WebContent/componentes/sidebar.jsp" />

    <div class="content">
        <h1>Gestión de Categorías</h1>

        <button id="btnAbrirModalCategoria" class="btn-agregar">
            <i class="fas fa-plus"></i> Agregar Nueva Categoría
        </button>


        <div class="buscador-categorias">
            <div class="buscador-wrapper">
                <i class="fas fa-search"></i>
                <input type="text" id="inputBuscarCategoria"
                       placeholder="Buscar"
                       value="${nombreBuscado != null ? nombreBuscado : ''}" />
            </div>
            <button onclick="limpiarBusqueda()" class="btn-limpiar">Limpiar</button>
        </div>


        <div class="table-container">
            <table>
                <thead>
                <tr>
                    <th class="oculto">ID</th>
                    <th>Nombre</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="categoria" items="${categorias}">
                    <tr data-nombre="${categoria.nombre}">
                        <td class="oculto">${categoria.idCategoria}</td>
                        <td>${categoria.nombre}</td>
                        <td>
                            <c:choose>
                                <c:when test="${categoria.estado == 1}">ACTIVO</c:when>
                                <c:when test="${categoria.estado == 0}">INACTIVO</c:when>
                                <c:when test="${categoria.estado == 2}">ELIMINADO</c:when>
                                <c:otherwise>Desconocido</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <button
                                type="button"
                                class="action-btn btnEditarCategoria"
                                data-id="${categoria.idCategoria}"
                                data-nombre="${categoria.nombre}"
                                data-estado="${categoria.estado}">
                                <i class="fas fa-edit"></i> Editar
                            </button>

                            <form action="EliminarCategoriaServlet" method="get" style="display:inline;" class="form-eliminar">
                                <input type="hidden" name="idCategoria" value="${categoria.idCategoria}" />
                                <c:if test="${not empty nombreBuscado}">
                                    <input type="hidden" name="nombre" value="${nombreBuscado}" />
                                </c:if>
                                <button class="action-btn delete" type="submit">
                                    <i class="fas fa-trash-alt"></i> Eliminar
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

                <!-- Fila si no hay coincidencias -->
                <tr id="filaNoExiste" style="display: none;">
                    <td colspan="4" style="text-align: center; color: #888; padding: 1rem;">
                        No existe ninguna categoría con ese nombre.
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Alertas -->
<c:if test="${param.msg == 'exito'}">
    <jsp:include page="WebContent/componentes/alerta.jsp" />
</c:if>
<c:if test="${param.msg == 'eliminado'}">
    <jsp:include page="WebContent/componentes/alertaEliminado.jsp" />
</c:if>
<c:if test="${param.msg == 'editado'}">
    <jsp:include page="WebContent/componentes/alertaEditado.jsp" />
</c:if>
<c:if test="${param.msg == 'categoriaDuplicada'}">
    <jsp:include page="WebContent/componentes/errorCategoria.jsp" />
</c:if>

<!-- Modales -->
<jsp:include page="WebContent/componentes/modalRegistroCategoria.jsp" />
<jsp:include page="WebContent/componentes/modalEditarCategoria.jsp" />

<!-- Scripts -->
<script src="WebContent/js/panel/modalRegistroCategoria.js"></script>
<script src="WebContent/js/panel/modalEditarCategoria.js"></script>
<script src="WebContent/js/panel/eliminarCategoria.js"></script>
<script src="WebContent/js/panel/buscarCategoria.js"></script>

</body>
</html>
