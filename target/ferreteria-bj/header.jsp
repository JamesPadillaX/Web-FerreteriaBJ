<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Cliente" %>

<%
  Cliente c = (Cliente) request.getAttribute("cliente");
  if (c == null && session != null) {
    c = (Cliente) session.getAttribute("clienteLogueado");
  }
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="WebContent/css/web/header.css">
<link rel="stylesheet" href="WebContent/css/web/categorias.css">
<script src="https://unpkg.com/lucide@latest"></script>
<script defer src="WebContent/js/web/header.js"></script>
<script defer src="WebContent/js/web/categorias.js"></script>
<header class="main-header">
  <div class="header-container">
    <div class="logo">
      <h1>Ferretería <span>BJ</span></h1>
    </div>

    <button class="hamburger" id="hamburgerBtn">
      <i data-lucide="menu"></i>
    </button>

    <nav class="nav-bar" id="navMenu">
      <ul>
        <li><a href="inicio">Inicio</a></li>
        <li><a href="#" id="toggle-categorias">Categorías</a></li>
        <li><a href="#">Ofertas</a></li>
        <li><a href="ayuda.jsp">Ayuda</a></li>
      </ul>
    </nav>

    <div class="user-actions">
      <% if (c != null) { %>
        <div class="user-panel">
          <span id="userMenuBtn" class="user-name">
            <i data-lucide="user" class="icon"></i> <%= c.getNombre() %>
          </span>
          <a href="carrito.jsp" class="carrito-icon" title="Carrito">
            <i data-lucide="shopping-cart" class="icon"></i>
          </a>
        </div>
      <% } else { %>
        <a href="login.jsp" class="btn-login">Iniciar Sesión</a>
        <a href="registro.jsp" class="btn-registro">Crear Cuenta</a>
      <% } %>
    </div>
  </div>
</header>


<div id="categoriasDropdown" class="dropdown-categorias" style="display: none;">
  <div id="categorias-container"></div>
</div>

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

<script>
  lucide.createIcons();
</script>
