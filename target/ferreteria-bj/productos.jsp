<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Productos - Ferretería BJ</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
  <link rel="stylesheet" href="WebContent/css/web/productos.css" />
</head>
<body class="pagina-productos">

<main class="contenedor-productos">

  <!-- === BUSCADOR === -->
  <section class="seccion-busqueda">
    <form action="productos" method="get" class="form-busqueda">
      <input type="text" name="query" placeholder="Buscar herramientas..." value="${param.query}">
      <button type="submit"><i class="fas fa-search"></i></button>
    </form>
  </section>

  <!-- === PRODUCTOS MÁS VENDIDOS === -->
  <section class="seccion-mas-vendidos">
    <h2>Productos más vendidos</h2>

    <div class="productos-contenedor">
      <c:forEach var="vendido" items="${productosMasVendidos}">
        <a href="DetalleProducto?id=${vendido.idProducto}" class="producto">
          <div class="etiqueta-preciazo">💥 PRECIAZO</div>
          <img src="${pageContext.request.contextPath}/${vendido.imagen}" alt="${vendido.nombre}">
          <p class="nombre">${vendido.nombre}</p>
          <p class="precio">S/ ${vendido.precio}</p>
        </a>
      </c:forEach>
    </div>

    <div class="productos-indicadores"></div>
  </section>

  <!-- === TODOS LOS PRODUCTOS === -->
  <section class="seccion-todos">
    <div class="encabezado">
      <h2>Todos los productos</h2>
    </div>

    <div class="grid-productos">
      <c:choose>
        <c:when test="${empty productos}">
          <div class="sin-productos">
            <p>No hay productos disponibles.</p>
          </div>
        </c:when>
        <c:otherwise>
          <c:forEach var="prod" items="${productos}">
            <a href="DetalleProducto?id=${prod.idProducto}" class="tarjeta-prod">
              <img src="${pageContext.request.contextPath}/${prod.imagen}" alt="${prod.nombre}">
              <h3>${prod.nombre}</h3>
              <p class="precio">S/ ${prod.precio}</p>
            </a>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </div>
  </section>

</main>

<%@ include file="footer.jsp" %>
<%@ include file="asistente.jsp" %>

<script>
document.addEventListener("DOMContentLoaded", () => {
  const contenedor = document.querySelector(".productos-contenedor");
  const productos = document.querySelectorAll(".producto");
  const indicadores = document.querySelector(".productos-indicadores");

  if (productos.length > 0) {
    const cantidadPorVista = window.innerWidth < 768 ? 1 : 3;
    const cantidadPaginas = Math.ceil(productos.length / cantidadPorVista);

    for (let i = 0; i < cantidadPaginas; i++) {
      const punto = document.createElement("div");
      punto.classList.add("indicador");
      if (i === 0) punto.classList.add("activo");

      punto.addEventListener("click", () => {
        const anchoProducto = productos[0].offsetWidth + 20;
        contenedor.scrollLeft = i * anchoProducto * cantidadPorVista;

        indicadores.querySelectorAll(".indicador").forEach(p => p.classList.remove("activo"));
        punto.classList.add("activo");
      });

      indicadores.appendChild(punto);
    }
  }
});
</script>

</body>
</html>
