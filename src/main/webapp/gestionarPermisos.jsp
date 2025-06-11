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
    <meta charset="UTF-8" />
    <title>Gestionar Permisos</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="WebContent/css/panel/panelPrincipal.css" />
    <link rel="stylesheet" href="WebContent/css/panel/gestionarPermisos.css" />
    <link rel="stylesheet" href="WebContent/css/panel/modalPermiso.css" />
    
</head>
<body>
    <div class="container">
        <jsp:include page="WebContent/componentes/sidebar.jsp" />

        <div class="content">
            <h1>Gestión de Permisos por Rol</h1>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Rol</th>
                            <th>Estado</th>
                            <th>Permisos</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="rol" items="${roles}">
                            <tr>
                                <td>${rol.idRol}</td>
                                <td>${rol.nombre}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${rol.estado == 1}">ACTIVO</c:when>
                                        <c:when test="${rol.estado == 0}">INACTIVO</c:when>
                                        <c:otherwise>DESCONOCIDO</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <button 
                                        class="btnPermisos" 
                                        data-idrol="${rol.idRol}" 
                                        data-nombrerol="${rol.nombre}"
                                        onclick="abrirModal(this)"
                                        title="Asignar permisos"
                                    >
                                        <i class="fas fa-key"></i> Permisos
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <c:if test="${param.msg == 'permisosEditados'}">
        <jsp:include page="WebContent/componentes/permisosActualizados.jsp" />
    </c:if>
 
    <div id="modalPermisos" class="modal"></div>

    <script src="WebContent/js/panel/gestionarPermisos.js"></script>
</body>
</html>
