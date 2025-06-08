<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Cliente" %>
<%
  HttpSession sesion = request.getSession(false);
  Cliente cliente = (sesion != null) ? (Cliente) sesion.getAttribute("clienteLogueado") : null;
%>


<link rel="stylesheet" href="WebContent/css/web/categorias.css">
<link rel="stylesheet" href="WebContent/css/web/styles.css">


<script src="WebContent/js/web/categorias.js" defer></script>
<script src="WebContent/js/web/header.js" defer></script>

<header class="main-header">
  <div class="logo">
    <h1>Ferretería <span>BJ</span></h1>
  </div>

  <nav class="nav-bar">
    <ul>
      <li><a href="index.jsp">Inicio</a></li>
      <li><a href="#" id="toggle-categorias">Categorías</a></li>
      <li><a href="#">Ofertas</a></li>
      <li><a href="#">Ayuda</a></li>
    </ul>
  </nav>

  <div class="user-actions">
    <% if (cliente != null) { %>
      <span id="userMenuBtn" class="user-name">👤 Hola, <%= cliente.getNombre() %></span>
      <a href="carrito.jsp" class="carrito-icon">🛒</a>
    <% } else { %>
      <a href="login.jsp">Iniciar Sesión</a>
      <a href="registro.jsp">Crear Cuenta</a>
    <% } %>
  </div>
</header>

<div id="blur-overlay" class="blur-overlay" style="display: none;"></div>

<div id="categoriasDropdown" class="dropdown-categorias" style="display: none;">
  <div id="categorias-container"></div>
</div>

<!-- Modal de Usuario -->
<% if (cliente != null) { %>
  <div id="userModal" class="modal">
    <div class="modal-content">
      <ul>
        <li><a href="perfil.jsp">👤 Mi Perfil</a></li>
        <li><a href="pedidos.jsp">📦 Mis Pedidos</a></li>
        <li><a href="LogoutServlet">🔒 Cerrar Sesión</a></li>
      </ul>
    </div>
  </div>
<% } %>
