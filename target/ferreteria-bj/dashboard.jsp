<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                <div class="stat-card purple">
                    <h2>Productos Activos</h2>
                </div>
                <div class="stat-card indigo">
                    <h2>Usuarios Activos</h2>
                    <p>${totalUsuariosActivos}</p>
                </div>
                <div class="stat-card violet">
                    <h2>Categorías Activas</h2>
                </div>
                <div class="stat-card emerald">
                    <h2>Ganancias Mensuales</h2>
                </div>
            </div>
</body>
</html>

