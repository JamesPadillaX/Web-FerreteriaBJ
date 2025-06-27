<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Cliente" %>

<%
  Cliente c = (Cliente) request.getAttribute("cliente");
  if (c == null && session != null) {
    c = (Cliente) session.getAttribute("clienteLogueado");
  }
%>

<!-- Enlaces CSS -->
<link rel="stylesheet" href="WebContent/css/web/categorias.css">
<link rel="stylesheet" href="WebContent/css/web/styles.css">

<!-- Lucide Icons CDN -->
<script src="https://unpkg.com/lucide@latest"></script>

<!-- Enlaces JS -->
<script src="WebContent/js/web/categorias.js" defer></script>
<script src="WebContent/js/web/header.js" defer></script>

<!-- Header -->
<header class="main-header">
  <div class="logo">
    <h1>Ferretería <span>BJ</span></h1>
  </div>

  <nav class="nav-bar">
    <ul>
      <li><a href="index.jsp">Inicio</a></li>
      <li><a href="#" id="toggle-categorias">Categorías</a></li>
      <li><a href="#">Ofertas</a></li>
      <li><a href="ayuda.jsp">Ayuda</a></li>
    </ul>
  </nav>

<div class="user-actions">
  <% if (c != null) { %>
    <div class="user-panel">
      <span id="userMenuBtn" class="user-name">
        <i data-lucide="user" class="icon"></i> Hola, <%= c.getNombre() %>
      </span>
      <a href="carrito.jsp" class="carrito-icon">
        <i data-lucide="shopping-cart" class="icon"></i>
      </a>
    </div>
  <% } else { %>
    <a href="login.jsp">Iniciar Sesión</a>
    <a href="registro.jsp">Crear Cuenta</a>
  <% } %>
</div>

</header>

<!-- Overlay y dropdown -->
<div id="blur-overlay" class="blur-overlay" style="display: none;"></div>
<div id="categoriasDropdown" class="dropdown-categorias" style="display: none;">
  <div id="categorias-container"></div>
</div>

<!-- Modal usuario -->
<% if (c != null) { %>
  <div id="userModal" class="modal">
    <div class="modal-content">
      <ul>
        <li><a href="perfilCliente.jsp"><i data-lucide="user-circle" class="icon"></i> Mi Perfil</a></li>
        <li><a href="pedidos.jsp"><i data-lucide="package" class="icon"></i> Mis Pedidos</a></li>
        <li><a href="LogoutServlet"><i data-lucide="log-out" class="icon"></i> Cerrar Sesión</a></li>
      </ul>
    </div>
  </div>
<% } %>

<!-- Lucide Init -->
<script>
  lucide.createIcons();
</script>
