<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Ferretería BJ</title>
  <link rel="stylesheet" href="WebContent/css/web/styles.css">
  <script defer src="WebContent/js/web/main.js"></script>
  <script src="WebContent/js/web/header.js"></script>
</head>
<body>
  <div class="contenedor-principal">
    <jsp:include page="header.jsp" />

    <main>
      <section class="hero">
        <h1>Bienvenido a Ferretería BJ</h1>
        <p>Encuentra todo lo que necesitas para tu proyecto</p>
      </section>
<section>
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

<section>
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
