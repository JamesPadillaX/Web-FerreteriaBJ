<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="modelo.Cliente" %>

<%
  Cliente c = (Cliente) request.getAttribute("cliente");
  if (c == null && session != null) {
    c = (Cliente) session.getAttribute("clienteLogueado");
  }
%>

<!-- Bootstrap y estilos -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
<link href="WebContent/css/web/header.css" rel="stylesheet" />

<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<nav class="navbar navbar-expand-lg navbar-custom fixed-top shadow-sm px-3">
  <div class="d-flex w-100 justify-content-between align-items-center">

    <!-- Botón hamburguesa (abre offcanvas principal) -->
    <button class="btn btn-outline-secondary d-lg-none" type="button" data-bs-toggle="offcanvas" data-bs-target="#navbarOffcanvas" aria-controls="navbarOffcanvas">
      <i class="bi bi-list fs-3"></i>
    </button>

    <!-- LOGO centrado -->
    <a class="navbar-brand mx-auto fw-bold d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/inicio">
      <span>Ferretería <span class="text-warning">BJ</span></span>
    </a>

    <!-- Iconos móviles -->
    <div class="d-flex align-items-center gap-2">
      <% if (c != null) { %>
        <a class="nav-link position-relative d-lg-none bg-secondary text-white rounded-circle p-2" 
           href="#" data-bs-toggle="offcanvas" data-bs-target="#perfilOffcanvas" aria-controls="perfilOffcanvas">
          <i class="bi bi-person-circle fs-4"></i>
        </a>
        <a class="nav-link position-relative d-lg-none bg-secondary text-white rounded-circle p-2" 
           href="${pageContext.request.contextPath}/carrito.jsp">
          <i class="bi bi-cart fs-4"></i>
        </a>
      <% } else { %>
        <a class="nav-link position-relative d-lg-none bg-secondary text-white rounded-circle p-2" 
           href="${pageContext.request.contextPath}/login.jsp">
          <i class="bi bi-box-arrow-in-right fs-4"></i>
        </a>
        <a class="nav-link position-relative d-lg-none bg-secondary text-white rounded-circle p-2" 
           href="${pageContext.request.contextPath}/registro.jsp">
          <i class="bi bi-person-plus fs-4"></i>
        </a>
      <% } %>
    </div>

    <!-- Menú desktop -->
    <div class="d-none d-lg-flex w-100 justify-content-between align-items-center">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0 fw-semibold">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/inicio">Inicio</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/productos">Productos</a></li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="categoriasDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Categorías
          </a>
          <ul class="dropdown-menu shadow-sm" aria-labelledby="categoriasDropdown">
            <c:forEach var="cat" items="${listaCategorias}">
              <li>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/Categoria?id=${cat.idCategoria}">
                  ${cat.nombre}
                </a>
              </li>
            </c:forEach>
          </ul>
        </li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/ofertas.jsp">Ofertas</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/ayuda.jsp">Ayuda</a></li>
      </ul>

      <!-- Panel usuario -->
      <ul class="navbar-nav mb-2 mb-lg-0 d-flex align-items-center">
        <% if (c != null) { %>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              <i class="bi bi-person-circle me-1"></i> <%= c.getNombre() %>
            </a>
            <ul class="dropdown-menu dropdown-menu-end shadow-sm">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/perfilCliente.jsp"><i class="bi bi-person me-2"></i> Mi Perfil</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Compras"><i class="bi bi-receipt me-2"></i> Mis Compras</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/LogoutServlet"><i class="bi bi-box-arrow-right me-2"></i> Cerrar Sesión</a></li>
            </ul>
          </li>

          <li class="nav-item ms-3">
            <a class="nav-link position-relative" href="${pageContext.request.contextPath}/carrito.jsp">
              <i class="bi bi-cart fs-5"></i>
            </a>
          </li>
        <% } else { %>
          <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/login.jsp"><i class="bi bi-box-arrow-in-right me-2"></i> Iniciar sesión</a></li>
          <li class="nav-item ms-2"><a class="nav-link" href="${pageContext.request.contextPath}/registro.jsp"><i class="bi bi-person-plus me-2"></i> Registrarse</a></li>
        <% } %>
      </ul>
    </div>
  </div>
</nav>

<!-- Offcanvas Menú (izquierda) - Principal -->
<div class="offcanvas offcanvas-start" tabindex="-1" id="navbarOffcanvas" aria-labelledby="navbarOffcanvasLabel" style="width: 250px;">
  <div class="offcanvas-header">
    <h5 class="offcanvas-title fw-bold" id="navbarOffcanvasLabel">Menú</h5>
    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Cerrar"></button>
  </div>
  <div class="offcanvas-body">
    <ul class="list-group list-group-flush">
      <li class="list-group-item"><a class="nav-link" href="${pageContext.request.contextPath}/inicio">Inicio</a></li>
      <li class="list-group-item"><a class="nav-link" href="${pageContext.request.contextPath}/productos">Productos</a></li>

      <!-- Abrir Offcanvas de Categorías -->
      <li class="list-group-item">
        <a class="nav-link" data-bs-toggle="offcanvas" href="#categoriasOffcanvas" role="button" aria-controls="categoriasOffcanvas">
          Categorías
        </a>
      </li>

      <li class="list-group-item"><a class="nav-link" href="${pageContext.request.contextPath}/ofertas.jsp">Ofertas</a></li>
      <li class="list-group-item"><a class="nav-link" href="${pageContext.request.contextPath}/ayuda.jsp">Ayuda</a></li>
    </ul>
  </div>
</div>

<!-- Offcanvas Categorías (izquierda) -->
<div class="offcanvas offcanvas-start" tabindex="-1" id="categoriasOffcanvas" aria-labelledby="categoriasOffcanvasLabel" style="width: 250px;">
  <div class="offcanvas-header">
    <h5 class="offcanvas-title fw-bold" id="categoriasOffcanvasLabel">Categorías</h5>
    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Cerrar"></button>
  </div>
  <div class="offcanvas-body">
    <button class="btn btn-outline-secondary mb-3" data-bs-toggle="offcanvas" data-bs-target="#navbarOffcanvas" aria-controls="navbarOffcanvas">
      <i class="bi bi-arrow-left me-2"></i> Volver al menú
    </button>

    <ul class="list-group list-group-flush">
      <c:forEach var="cat" items="${listaCategorias}">
        <li class="list-group-item">
          <a class="text-decoration-none d-block py-2" href="${pageContext.request.contextPath}/Categoria?id=${cat.idCategoria}">
            ${cat.nombre}
          </a>
        </li>
      </c:forEach>
    </ul>
  </div>
</div>

<!-- Offcanvas Perfil (derecha) -->
<div class="offcanvas offcanvas-end" tabindex="-1" id="perfilOffcanvas" aria-labelledby="perfilOffcanvasLabel" style="width: 250px;">
  <div class="offcanvas-header">
    <h5 class="offcanvas-title fw-bold" id="perfilOffcanvasLabel">Perfil</h5>
    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Cerrar"></button>
  </div>
  <div class="offcanvas-body p-0">
    <ul class="list-group list-group-flush">
      <li class="list-group-item"><a href="${pageContext.request.contextPath}/perfilCliente.jsp" class="text-dark text-decoration-none"><i class="bi bi-person me-2"></i> Mi Perfil</a></li>
      <li class="list-group-item"><a href="${pageContext.request.contextPath}/Compras" class="text-dark text-decoration-none"><i class="bi bi-receipt me-2"></i> Mis Compras</a></li>
      <li class="list-group-item"><a href="${pageContext.request.contextPath}/LogoutServlet" class="text-danger text-decoration-none"><i class="bi bi-box-arrow-right me-2"></i> Cerrar sesión</a></li>
    </ul>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
document.addEventListener("DOMContentLoaded", () => {
  const perfilBtn = document.getElementById("perfil-btn");
  const perfilDropdown = document.getElementById("perfil-dropdown");

  if (window.innerWidth < 768) {
    perfilBtn.addEventListener("click", (e) => {
      e.stopPropagation();
      perfilDropdown.classList.toggle("active");
    });

    document.addEventListener("click", (e) => {
      if (!perfilDropdown.contains(e.target) && e.target !== perfilBtn) {
        perfilDropdown.classList.remove("active");
      }
    });
  }
});
</script>
<script>
window.addEventListener("scroll", () => {
  const navbar = document.querySelector(".navbar-custom");
  navbar.classList.toggle("scrolled", window.scrollY > 10);
});
</script>
