<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>${nombreCategoria}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="WebContent/css/web/cagetoriaProductos.css" />
</head>

<body class="pagina-productos">
<%@ include file="header.jsp" %>

<main class="contenedor-productos">

    <c:if test="${not empty error}">
        <p style="color:red; font-weight:bold; margin: 20px 0;">${error}</p>
    </c:if>

    <div class="encabezado-productos">
        <h1 class="titulo-categoria">${nombreCategoria}</h1>

        <div class="barra-superior">
            <form method="get" class="form-orden">
                <input type="hidden" name="id" value="${idCategoria}" />
                <label for="orden">Ordenar por</label>
                <select name="orden" id="orden" onchange="this.form.submit()">
                    <option value="recomendado" ${orden == null || orden == 'recomendado' ? 'selected' : ''}>Más relevantes</option>
                    <option value="precio_asc" ${orden == 'precio_asc' ? 'selected' : ''}>Menor precio</option>
                    <option value="precio_desc" ${orden == 'precio_desc' ? 'selected' : ''}>Mayor precio</option>
                </select>
            </form>
        </div>
    </div>

    <div class="grid-productos">
        <c:choose>
            <c:when test="${empty productos}">
                <div class="mensaje-sin-productos">
                    <p>No hay productos disponibles en esta categoría.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="prod" items="${productos}">
                    <div class="tarjeta-producto">
                        <img class="img-prod" src="${pageContext.request.contextPath}/${prod.imagen}" alt="${prod.nombre}" />
                        <div class="cuerpo-tarjeta">
                            <h3 class="nombre-prod">${prod.nombre}</h3>
                            <p class="descripcion-prod">${prod.descripcion}</p>
                            <p class="precio-prod">S/ ${prod.precio}</p>
                            <div class="acciones-prod">
                                <form action="AgregarAlCarritoServlet" method="post">
                                    <input type="hidden" name="idProducto" value="${prod.idProducto}" />
                                    <input type="hidden" name="cantidad" value="1" />
                                    <button type="submit" class="btn-agregar" title="Agregar al carrito">
                                        <i class="fas fa-cart-plus"></i>
                                    </button>
                                </form>
                                <form action="DetalleProducto" method="get">
                                    <input type="hidden" name="id" value="${prod.idProducto}" />
                                    <button type="submit" class="btn-detalle" title="Ver detalles">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<%@ include file="footer.jsp" %>
</body>
</html>
