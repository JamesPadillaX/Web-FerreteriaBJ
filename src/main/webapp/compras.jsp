<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Compras - Ferretería BJ</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="WebContent/css/web/compras.css">
</head>
<body>
<div class="layout">

    <main class="compras-contenido">
        <h1><i class="fas fa-shopping-bag"></i> Mis Compras</h1>

        <c:choose>
            <c:when test="${empty comprasAgrupadas}">
                <p class="mensaje-vacio">Aún no has realizado compras.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="fecha" items="${comprasAgrupadas.keySet()}">
                    <section class="grupo-fecha">
                        <h2 class="titulo-fecha">${fecha}</h2>
                        <div class="grupo-productos">
                            <c:forEach var="producto" items="${comprasAgrupadas[fecha]}">
                                <div class="tarjeta-producto">
                                    <div class="imagen-producto">
                                        <img src="${pageContext.request.contextPath}/${producto.imagenProducto}" alt="Producto">
                                    </div>
                                    <div class="info-producto">
                                        <p class="estado
                                            ${producto.estado == 'PAGADO' ? 'estado-pagado' : 
                                              producto.estado == 'ENTREGADO' ? 'estado-entregado' : 'estado-pendiente'}">
                                            ${producto.estado != null ? producto.estado : "Pendiente"}
                                        </p>
                                        <h3>
                                            <a href="DetalleProducto?id=${producto.idProducto}" class="nombre-producto-link">
                                                ${producto.nombreProducto}
                                            </a>
                                            <c:if test="${producto.cantidad > 1}">
                                            ×${producto.cantidad}
                                        </c:if>
                                    </h3>

                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </section>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </main>

    <%@ include file="footer.jsp" %>
</div>
</body>
</html>
