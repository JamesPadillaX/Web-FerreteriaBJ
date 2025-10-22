<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Ferretería BJ</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="WebContent/css/web/index.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
  <script defer src="WebContent/js/web/carrusel.js"></script>
</head>
<body>
  
  <div class="contenedor-principal">
    
    <main>
      <section class="hero">
        <div class="carrusel" id="carrusel">
          <c:forEach var="item" items="${carruseles}" varStatus="st">
            <div class="slide ${st.index == 0 ? 'active' : ''}">
              <img src="${pageContext.request.contextPath}/${item.rutaImagen}" alt="${item.titulo}" />
            </div>
          </c:forEach>
          <c:if test="${fn:length(carruseles) > 1}">
            <div class="carrusel-indicadores" id="indicadoresCarrusel"></div>
          </c:if>
        </div>
      </section>

      <section class="fondo-azul">
        <div class="bienvenida">
          <h1>Bienvenido a <span>Ferretería BJ</span></h1>
          <p>Encuentra todo lo que necesitas para tu proyecto</p>
        </div>
      </section>

<section class="ofertas">
    <h2>Si buscas <span>herramientas</span> de todo tipo...</h2>
    <div class="productos-contenedor">
        <c:forEach var="producto" items="${productosCategoria1}">
            <a href="DetalleProducto?id=${producto.idProducto}" class="producto">
                <img src="${pageContext.request.contextPath}/${producto.imagen}" alt="${producto.nombre}">
                <p class="nombre">${producto.nombre}</p>
                <p class="precio">S/ ${producto.precio}</p>
            </a>
        </c:forEach>
    </div>
</section>

<section class="ofertas">
    <h2>Para la <span>construcción</span> lo mejor para tu obra...</h2>
    <div class="productos-contenedor">
        <c:forEach var="producto" items="${productosCategoria2}">
            <a href="DetalleProducto?id=${producto.idProducto}" class="producto">
                <img src="${pageContext.request.contextPath}/${producto.imagen}" alt="${producto.nombre}">
                <p class="nombre">${producto.nombre}</p>
                <p class="precio">S/ ${producto.precio}</p>
            </a>
        </c:forEach>
    </div>
</section>


<script>
document.addEventListener("DOMContentLoaded", () => {
  const slides = document.querySelectorAll(".slide");
  const contenedorIndicadores = document.getElementById("indicadoresCarrusel");
  let index = 0;

  function mostrarSlide(i) {
    slides.forEach((slide, idx) => {
      slide.classList.toggle("active", idx === i);
      contenedorIndicadores?.children[idx]?.classList.toggle("activo", idx === i);
    });
  }

  if (contenedorIndicadores) {
    slides.forEach((_, i) => {
      const indicador = document.createElement("div");
      indicador.classList.add("indicador");
      if (i === 0) indicador.classList.add("activo");
      indicador.addEventListener("click", () => {
        index = i;
        mostrarSlide(index);
      });
      contenedorIndicadores.appendChild(indicador);
    });
  }

  setInterval(() => {
    index = (index + 1) % slides.length;
    mostrarSlide(index);
  }, 5000);


  const todasSecciones = document.querySelectorAll('.ofertas');

  todasSecciones.forEach(seccion => {
    const contenedorProductos = seccion.querySelector('.productos-contenedor');
    const productos = seccion.querySelectorAll('.producto');

    if (productos.length > 0) {
      const indicadores = document.createElement('div');
      indicadores.className = 'productos-indicadores';

      const cantidadPaginas = Math.ceil(productos.length / 5);

      for (let i = 0; i < cantidadPaginas; i++) {
        const punto = document.createElement('div');
        punto.classList.add('indicador');
        if (i === 0) punto.classList.add('activo');

        punto.addEventListener('click', () => {
          const anchoProducto = productos[0].offsetWidth + 20; 
          contenedorProductos.scrollLeft = i * anchoProducto * 5;

          indicadores.querySelectorAll('.indicador').forEach(p => p.classList.remove('activo'));
          punto.classList.add('activo');
        });

        indicadores.appendChild(punto);
      }

      seccion.appendChild(indicadores);
    }
  });

});
</script>

    <%@ include file="footer.jsp" %>
  </div>
  <%@ include file="asistente.jsp" %>
</body>
</html>
