<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de Venta #${idVenta}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="WebContent/css/panel/detalleVenta.css">
</head>
<body>

<div class="container">
    <jsp:include page="WebContent/componentes/sidebar.jsp" />

    <div class="content">
        <h1>Detalle de Venta #${idVenta}</h1>

        <c:if test="${empty detallesVenta}">
            <div class="fila-vacia">No hay detalles para esta venta.</div>
        </c:if>

        <c:if test="${not empty detallesVenta}">
            <div class="tabla-ventas">
                <table>
                    <thead>
                        <tr>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio Unitario</th>
                            <th>Subtotal</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="d" items="${detallesVenta}">
                            <tr>
                                <td>${d.nombreProducto}</td>
                                <td>${d.cantidad}</td>
                                <td>S/. ${d.precioUnitario}</td>
                                <td>S/. ${d.subtotal}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <div style="margin-top: 20px;">
            <a href="Ventas" class="btn-limpiar">
                <i class="fas fa-arrow-left"></i> Regresar
            </a>
        </div>
    </div>
</div>

</body>
</html>
