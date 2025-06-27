<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestionar Ventas</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="WebContent/css/panel/gestionarVentas.css">
</head>
<body>
    <div class="container">
        <jsp:include page="WebContent/componentes/sidebar.jsp" />

        <div class="content">
            <h1>Gestión de Ventas</h1>

            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Cliente</th>
                            <th>Usuario</th>
                            <th>Total</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="venta" items="${ventas}">
                            <tr>
                                <td>${venta.idVenta}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty venta.clienteNombre}">
                                            ${venta.clienteNombre}
                                        </c:when>
                                        <c:otherwise>No registrado</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty venta.usuarioNombre}">
                                            ${venta.usuarioNombre}
                                        </c:when>
                                        <c:otherwise>Cliente web</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>S/ ${venta.total}</td>
                                <td>${venta.fecha}</td>
                                <td>${venta.estado}</td>
                                <td>
                                    <form action="VerDetalleVentaServlet" method="get" style="display:inline;">
                                        <input type="hidden" name="idVenta" value="${venta.idVenta}" />
                                        <button class="action-btn" type="submit">
                                            <i class="fas fa-eye"></i> Ver Detalles
                                        </button>
                                    </form>

                                    <form action="CambiarEstadoVentaServlet" method="post" style="display:inline;">
                                        <input type="hidden" name="idVenta" value="${venta.idVenta}" />
                                        <button class="action-btn" type="submit">
                                            <i class="fas fa-sync"></i> Cambiar Estado
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

</body>
</html>
