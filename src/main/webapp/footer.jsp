<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Font Awesome para íconos -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<style>
  /* =====================================================
     🔹 FOOTER FERRETERÍA BJ - Versión Final Corregida
     Compatible con header fixed-top y sin scroll solapado
  ====================================================== */

  /* --- Estructura base --- */
  html, body {
    height: 100%;
    margin: 0;
    padding: 0;
    overflow-x: hidden; /* Evita scroll lateral */
    box-sizing: border-box;
  }

  /* --- Corrige solapamiento con header fijo --- */
  body {
    padding-top: 70px; /* Ajusta según la altura exacta del header */
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    font-family: 'Poppins', 'Segoe UI', sans-serif;
  }

  main {
    flex: 1; /* Empuja el footer al fondo */
  }

  /* --- Footer principal --- */
  footer.main-footer {
    background: linear-gradient(180deg, #007bff 0%, #0056b3 100%);
    color: #fff;
    padding: 2rem 1rem 1rem;
    margin-top: auto;
    width: 100%;
    max-width: 100vw;
    box-sizing: border-box;
    overflow-x: clip;
  }

  /* --- Contenedor interno --- */
  .footer-contenido {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    flex-wrap: wrap;
    max-width: 1200px;
    margin: 0 auto;
    gap: 2rem;
  }

  /* --- Logo y descripción --- */
  .footer-logo h2 {
    font-size: 1.8rem;
    color: #fff;
    margin: 0;
  }

  .footer-logo h2 span {
    color: #ffc107; /* Dorado */
  }

  .footer-logo p {
    margin-top: 0.5rem;
    color: #e0e0e0;
  }

  /* --- Contacto --- */
  .footer-contacto h3,
  .footer-redes h3 {
    color: #ffc107;
    margin-bottom: 0.8rem;
    font-size: 1.1rem;
  }

  .footer-contacto ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }

  .footer-contacto li {
    margin: 0.4rem 0;
    color: #f8f8f8;
    font-size: 0.95rem;
  }

  .footer-contacto i {
    margin-right: 0.5rem;
    color: #ffc107;
  }

  /* --- Redes sociales --- */
  .footer-redes .redes-iconos {
    display: flex;
    gap: 1rem;
  }

  .footer-redes .redes-iconos a {
    color: #fff;
    font-size: 1.4rem;
    transition: color 0.3s ease, transform 0.3s ease;
  }

  .footer-redes .redes-iconos a:hover {
    color: #ffc107;
    transform: scale(1.15);
  }

  /* --- Línea inferior --- */
  .footer-copy {
    text-align: center;
    margin-top: 1.5rem;
    border-top: 1px solid rgba(255, 255, 255, 0.2);
    padding-top: 1rem;
    font-size: 0.9rem;
    color: #d9d9d9;
  }

  /* --- Responsive --- */
  @media (max-width: 768px) {
    .footer-contenido {
      flex-direction: column;
      align-items: center;
      text-align: center;
    }

    .footer-redes .redes-iconos {
      justify-content: center;
    }
  }
</style>

<!-- =====================================================
     🔹 ESTRUCTURA HTML DEL FOOTER
===================================================== -->
<footer class="main-footer">
  <div class="footer-contenido">
    <!-- LOGO -->
    <div class="footer-logo">
      <h2>Ferretería <span>BJ</span></h2>
      <p>Tu aliado en herramientas, materiales y construcción.</p>
    </div>

    <!-- CONTACTO -->
    <div class="footer-contacto">
      <h3>Contáctanos</h3>
      <ul>
        <li><i class="fas fa-envelope"></i> contacto@ferreteriabj.com</li>
        <li><i class="fas fa-phone"></i> 920 426 495</li>
        <li><i class="fas fa-globe"></i> www.ferreteriabj.com</li>
      </ul>
    </div>

    <!-- REDES -->
    <div class="footer-redes">
      <h3>Síguenos</h3>
      <div class="redes-iconos">
        <a href="#" title="Facebook"><i class="fab fa-facebook-f"></i></a>
        <a href="#" title="Instagram"><i class="fab fa-instagram"></i></a>
        <a href="#" title="WhatsApp"><i class="fab fa-whatsapp"></i></a>
      </div>
    </div>
  </div>

  <div class="footer-copy">
    <p>&copy; 2025 <strong>Ferretería BJ</strong> - Todos los derechos reservados.</p>
  </div>
</footer>
