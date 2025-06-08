<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%
    Object usuario = session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("loginUsuario.jsp");
        return; 
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Panel Principal - Ferretería BJ</title>
    <link rel="stylesheet" href="WebContent/css/panel/panelPrincipal.css" />
    <!-- Importar Font Awesome para iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>
    <div class="sidebar">
        <nav class="sidebar">
            <div class="sidebar-header">
                <h2>Ferretería BJ</h2>
            </div>
            <ul class="menu">
                <%
                    List<String> modulosPermitidos = (List<String>) session.getAttribute("modulosPermitidos");
                    if (modulosPermitidos == null) {
                        modulosPermitidos = java.util.Collections.emptyList();
                    }
                %>

                <li><a href="perfilUsuario.jsp"><i class="fas fa-user"></i> Perfil</a></li>

                <% if (modulosPermitidos.contains("Usuarios")) { %>
                    <li><a href="ListarUsuariosServlet"><i class="fas fa-users"></i> Usuarios</a></li>
                <% } %>

                <% if (modulosPermitidos.contains("Clientes")) { %>
                    <li><a href="errorFuncionalidades.jsp"><i class="fas fa-address-book"></i> Clientes</a></li>
                <% } %>

                <% if (modulosPermitidos.contains("Roles")) { %>
                    <li><a href="ListarRolesServlet"><i class="fas fa-user-tag"></i> Roles</a></li>
                <% } %>

                <% if (modulosPermitidos.contains("Permisos")) { %>
                    <li><a href="ListarRoles"><i class="fas fa-key"></i> Permisos</a></li>
                <% } %>

                <% if (modulosPermitidos.contains("Categorías")) { %>
                    <li><a href="ListarCategoriasServlet"><i class="fas fa-list"></i> Categorías</a></li>
                <% } %>

                <% if (modulosPermitidos.contains("Productos")) { %>
                    <li><a href="ListarProductosServlet"><i class="fas fa-box-open"></i> Productos</a></li>
                <% } %>

                <% if (modulosPermitidos.contains("Ventas")) { %>
                    <li><a href="errorFuncionalidades.jsp"><i class="fas fa-shopping-cart"></i> Ventas</a></li>
                <% } %>

                <% if (modulosPermitidos.contains("Reportes")) { %>
                    <li><a href="errorFuncionalidades.jsp"><i class="fas fa-chart-line"></i> Reportes</a></li>
                <% } %>

                <li><a href="LogoutUsuarioServlet"><i class="fas fa-sign-out-alt"></i> Cerrar sesión</a></li>
            </ul>
        </nav>
    </div>
</body>
</html>
