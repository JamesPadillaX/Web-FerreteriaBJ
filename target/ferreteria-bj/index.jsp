<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <jsp:include page="header.jsp" />

    <main>
      <!-- CARRUSEL -->
      <section class="hero">
        <div class="carrusel" id="carrusel">
          <c:forEach var="item" items="${carruseles}" varStatus="st">
            <div class="slide ${st.index == 0 ? 'active' : ''}">
              <img src="${pageContext.request.contextPath}/${item.rutaImagen}" alt="${item.titulo}" />
              <div class="carrusel-texto">
                <h2>${item.titulo}</h2>
                <p>${item.descripcion}</p>
              </div>
            </div>
          </c:forEach>

          <!-- Solo muestra las flechas si hay más de una imagen -->
          <c:if test="${fn:length(carruseles) > 1}">
            <button class="flecha izquierda" id="prevBtn"><i class="fas fa-chevron-left"></i></button>
            <button class="flecha derecha" id="nextBtn"><i class="fas fa-chevron-right"></i></button>
          </c:if>
        </div>
      </section>

      <!-- BIENVENIDA -->
      <section class="fondo-azul">
        <div class="bienvenida">
          <h1>Bienvenido a <span>Ferretería BJ</span></h1>
          <p>Encuentra todo lo que necesitas para tu proyecto</p>
        </div>
      </section>

      <!-- OFERTAS DESTACADAS -->
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

      <!-- LO MÁS VENDIDO -->
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

    <jsp:include page="footer.jsp" />
  </div>
</body>
</html>
