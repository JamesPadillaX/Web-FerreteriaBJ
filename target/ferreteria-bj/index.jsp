<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Ferretería BJ</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="WebContent/css/web/header.css" />
  <link rel="stylesheet" href="WebContent/css/web/index.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
  <script defer src="WebContent/js/web/carrusel.js"></script>
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

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
        <h2>Ofertas Destacadas</h2>
        <div class="productos-contenedor">
          <div class="producto">
            <img src="WebContent/images/Herramientas.png" alt="Taladro">
            <p>Taladro Eléctrico - S/ 199</p>
          </div>
          <div class="producto">
            <img src="WebContent/images/Taladro.png" alt="Martillo">
            <p>Martillo Profesional - S/ 49</p>
          </div>
        </div>
      </section>

      <section class="ofertas">
        <h2>Lo más vendido</h2>
        <div class="productos-contenedor">
          <div class="producto">
            <img src="images/destornillador.jpg" alt="Destornillador">
            <p>Set de Destornilladores - S/ 39</p>
          </div>
          <div class="producto">
            <img src="images/llave.jpg" alt="Llave">
            <p>Llave Stilson - S/ 59</p>
          </div>
        </div>
      </section>
    </main>

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
});

</script>
    <jsp:include page="footer.jsp" />
  </div>
</body>
</html>
