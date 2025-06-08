<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Verificar si usuario está en sesión
    Object usuario = session.getAttribute("usuario");
    if (usuario == null) {
        // No hay usuario autenticado, redirigir a login
        response.sendRedirect("loginUsuario.jsp");
        return; // Termina la ejecución del JSP aquí
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestionar Usuarios</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="WebContent/css/panel/gestionarUsuarios.css">
    <link rel="stylesheet" href="WebContent/css/panel/modalUsuario.css">
    <link rel="stylesheet" href="css/panel/modalEditarUsuario.css">
</head>

<body>
    <div class="container">
        <jsp:include page="WebContent/componentes/sidebar.jsp" />

        <div class="content">
            <h1>Gestión de Usuarios</h1>
            <div class="export-buttons" style="margin-bottom: 15px;">
    <a href="ExportarUsuariosPdfServlet" class="btn-exportar" style="margin-right: 10px;">
        <i class="fas fa-file-pdf"></i> Exportar PDF
    </a>
    <a href="ExportarUsuariosExcelServlet" class="btn-exportar">
        <i class="fas fa-file-excel"></i> Exportar Excel
    </a>
</div>


            <button id="btnAbrirModal" class="btn-agregar">+ Agregar Nuevo Usuario</button>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>DNI</th>
                            <th>Teléfono</th>
                            <th>Usuario</th>
                            <th>Contraseña</th>
                            <th>Rol</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usuarioItem" items="${usuarios}">
                            <tr>
                                <td>${usuarioItem.idUsuario}</td>
                                <td>${usuarioItem.nombre}</td>
                                <td>${usuarioItem.apellidos}</td>
                                <td>${usuarioItem.dni}</td>
                                <td>${usuarioItem.telefono}</td>
                                <td>${usuarioItem.username}</td>
                                <td>${usuarioItem.password}</td>
                                <td>${usuarioItem.rol.nombre}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${usuarioItem.estado == 1}">ACTIVO</c:when>
                                        <c:when test="${usuarioItem.estado == 0}">INACTIVO</c:when>
                                        <c:when test="${usuarioItem.estado == 2}">ELIMINADO</c:when>
                                        <c:otherwise>Desconocido</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <button 
                                        type="button"
                                        class="action-btn btnEditarUsuario"
                                        data-id="${usuarioItem.idUsuario}"
                                        data-nombre="${usuarioItem.nombre}"
                                        data-apellidos="${usuarioItem.apellidos}"
                                        data-dni="${usuarioItem.dni}"
                                        data-telefono="${usuarioItem.telefono}"
                                        data-username="${usuarioItem.username}"
                                        data-password="${usuarioItem.password}"
                                        data-rol="${usuarioItem.idRol}"
                                        data-estado="${usuarioItem.estado}"
                                    >
                                        <i class="fas fa-edit"></i> Editar
                                    </button>

                                    <form action="EliminarUsuarioServlet" method="get" style="display:inline;" class="form-eliminar">
                                        <input type="hidden" name="idUsuario" value="${usuarioItem.idUsuario}" />
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

    <c:if test="${param.msg == 'errorAdmin'}">
        <jsp:include page="WebContent/componentes/alertaAdmin.jsp" />
    </c:if>



    <jsp:include page="WebContent/componentes/modalRegistroUsuario.jsp" />
    <jsp:include page="WebContent/componentes/modalEditarUsuario.jsp" />

    <script src="WebContent/js/panel/modalUsuario.js"></script>
    <script src="js/panel/eliminarUsuario.js"></script>
    <script src="WebContent/js/panel/modalEditarUsuario.js"></script>
</body>
</html>
