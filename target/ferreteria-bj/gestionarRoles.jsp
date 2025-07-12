<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestionar Roles</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="WebContent/css/panel/gestionarRoles.css">
    <link rel="stylesheet" href="WebContent/css/panel/modalRegistroRol.css">
    <link rel="stylesheet" href="WebContent/css/panel/modalEditarRol.css">
</head>
<body>
    <div class="container">
        <jsp:include page="WebContent/componentes/sidebar.jsp" />

        <div class="content">
            <h1>Gestión de Roles</h1>

            <button id="btnAbrirModalRol" class="btn-agregar">+ Agregar Nuevo Rol</button>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre </th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="rolItem" items="${roles}">
                            <tr>
                                <td>${rolItem.idRol}</td>
                                <td>${rolItem.nombre}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${rolItem.estado == 1}">ACTIVO</c:when>
                                        <c:when test="${rolItem.estado == 0}">INACTIVO</c:when>
                                        <c:when test="${rolItem.estado == 2}">ELIMINADO</c:when>
                                        <c:otherwise>Desconocido</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <button 
                                        type="button"
                                        class="action-btn btnEditarRol"
                                        data-id="${rolItem.idRol}"
                                        data-nombre="${rolItem.nombre}"
                                        data-estado="${rolItem.estado}"
                                    >
                                        <i class="fas fa-edit"></i> Editar
                                    </button>

                                    <form action="EliminarRolServlet" method="get" style="display:inline;" class="form-eliminar">
                                        <input type="hidden" name="idRol" value="${rolItem.idRol}" />
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
    <c:if test="${param.msg == 'exito'}">
        <jsp:include page="WebContent/componentes/alerta.jsp" />
    </c:if>

    <c:if test="${param.msg == 'eliminado'}">
        <jsp:include page="WebContent/componentes/alertaEliminado.jsp" />
    </c:if>

    <c:if test="${param.msg == 'editado'}">
        <jsp:include page="WebContent/componentes/alertaEditado.jsp" />
    </c:if>

    <c:if test="${param.msg == 'rolDuplicado'}">
        <jsp:include page="WebContent/componentes/errorRol.jsp" />
    </c:if>

    <jsp:include page="WebContent/componentes/modalRegistroRol.jsp" />
    <jsp:include page="WebContent/componentes/modalEditarRol.jsp" />

    <script src="WebContent/js/panel/modalRegistroRol.js"></script>
    <script src="js/panel/eliminarRol.js"></script>
    <script src="WebContent/js/panel/modalEditarRol.js"></script>

</body>
</html>
