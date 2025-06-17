<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="WebContent/css/panel/dashboard.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="container">
        <jsp:include page="WebContent/componentes/sidebar.jsp" />

        <div class="content">
            <h1 class="dashboard-title">Dashboard</h1>

            <div class="card-grid">
                <div class="card-grid">

                    <a href="ListarUsuariosServlet" class="stat-card indigo">
                        <i class="fas fa-users fa-2x"></i> 
                        <h2>Usuarios Activos</h2>
                        <p>${totalUsuariosActivos}</p>
                    </a>
                    
                    <a href="ListarProductosServlet" class="stat-card purple">
                        <i class="fas fa-boxes fa-2x"></i> 
                        <h2>Productos Activos</h2>
                        <p>${totalProductosActivos}</p>
                    </a>

                    <a href="ListarCategoriasServlet" class="stat-card violet">
                        <i class="fas fa-tags fa-2x"></i> 
                        <h2>Categorías Activas</h2>
                        <p>${totalCategoriasActivas}</p>
                    </a>

                    <div class="stat-card emerald">
                        <i class="fas fa-dollar-sign fa-2x"></i> 
                        <h2>Ganancias Mensuales</h2>
                        <p>${gananciasMensuales}</p>
                    </div>

                    <div class="card bajo-stock">
                        <h2><i class="fas fa-triangle-exclamation"></i> Productos con Bajo Stock</h2>
                        <ul>
                            <c:forEach var="producto" items="${productosBajoStock}">
                            <li>
                                <strong>${producto.nombre}</strong> - Stock: <span style="color: red">${producto.cantidad}</span>
                            </li>
                        </c:forEach>
                        <c:if test="${empty productosBajoStock}">
                        <li>Todos los productos tienen stock suficiente</li>
                    </c:if>
                </ul>
            </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
