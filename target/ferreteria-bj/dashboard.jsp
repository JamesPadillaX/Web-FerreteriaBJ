<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Ferretería BJ</title>

    <!-- Tailwind CSS -->
    <script src="https://cdn.tailwindcss.com"></script>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <!-- Estilo adicional -->
    <link rel="stylesheet" href="WebContent/css/panel/dashboard.css">
</head>
<body class="bg-gray-50 flex">

    <jsp:include page="WebContent/componentes/sidebar.jsp" />

    <main class="flex-1 p-8 overflow-y-auto">
        <h1 class="text-3xl font-bold text-gray-800 mb-8">Dashboard Inteligente</h1>

        <!-- Tarjetas -->
        <div class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-6 mb-8">
            <div class="bg-white rounded-2xl p-6 shadow-md hover:shadow-lg transition">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-sm text-gray-500">Usuarios Activos</p>
                        <h2 class="text-2xl font-bold text-gray-800">${totalUsuariosActivos}</h2>
                    </div>
                    <div class="bg-blue-100 p-3 rounded-xl text-blue-600">
                        <i class="fa-solid fa-users"></i>
                    </div>
                </div>
            </div>

            <div class="bg-white rounded-2xl p-6 shadow-md hover:shadow-lg transition">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-sm text-gray-500">Productos Activos</p>
                        <h2 class="text-2xl font-bold text-gray-800">${totalProductosActivos}</h2>
                    </div>
                    <div class="bg-purple-100 p-3 rounded-xl text-purple-600">
                        <i class="fa-solid fa-boxes-stacked"></i>
                    </div>
                </div>
            </div>

            <div class="bg-white rounded-2xl p-6 shadow-md hover:shadow-lg transition">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-sm text-gray-500">Categorías Activas</p>
                        <h2 class="text-2xl font-bold text-gray-800">${totalCategoriasActivas}</h2>
                    </div>
                    <div class="bg-indigo-100 p-3 rounded-xl text-indigo-600">
                        <i class="fa-solid fa-tags"></i>
                    </div>
                </div>
            </div>

            <div class="bg-white rounded-2xl p-6 shadow-md hover:shadow-lg transition">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-sm text-gray-500">Ganancias (últimos 28 días)</p>
                        <h2 class="text-2xl font-bold text-gray-800">S/ ${totalGanancias28Dias}</h2>
                    </div>
                    <div class="bg-emerald-100 p-3 rounded-xl text-emerald-600">
                        <i class="fa-solid fa-sack-dollar"></i>
                    </div>
                </div>
            </div>
        </div>

<!-- Productos con Bajo Stock -->
<div class="bg-red-50 border border-red-200 rounded-2xl p-5 mb-8 shadow-sm">
    <div class="flex items-center mb-3">
        <div class="bg-red-100 text-red-600 p-2 rounded-xl mr-3">
            <i class="fa-solid fa-triangle-exclamation"></i>
        </div>
        <h2 class="text-lg font-semibold text-red-700">Productos con Bajo Stock</h2>
    </div>

    <c:if test="${not empty productosBajoStock}">
        <ul class="divide-y divide-red-100">
            <c:forEach var="producto" items="${productosBajoStock}">
                <li class="py-2 flex justify-between items-center">
                    <span class="font-medium text-gray-800">${producto.nombre}</span>
                    <span class="text-sm bg-red-100 text-red-700 font-semibold px-3 py-1 rounded-full">
                        Stock: ${producto.cantidad}
                    </span>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${empty productosBajoStock}">
        <p class="text-gray-600 text-sm italic">Todos los productos tienen stock suficiente.</p>
    </c:if>
</div>

        <!-- Gráficos -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <div class="bg-white rounded-2xl p-6 shadow-md">
                <h2 class="text-lg font-semibold text-gray-800 mb-4">
                    <i class="fa-solid fa-chart-column"></i> Top Productos Más Vendidos
                </h2>
                <canvas id="chartProductosVendidos"></canvas>
            </div>

            <div class="bg-white rounded-2xl p-6 shadow-md">
                <h2 class="text-lg font-semibold text-gray-800 mb-4">
                    <i class="fa-solid fa-pie-chart"></i> Categorías Más Vendidas
                </h2>
                <canvas id="chartCategorias"></canvas>
            </div>
        </div>
    </main>

    <script>
        const nombres = [
            <c:forEach var="p" items="${productosMasVendidos}">
                '${p.nombre}',
            </c:forEach>
        ];
        const cantidades = [
            <c:forEach var="p" items="${productosMasVendidos}">
                ${p.totalVendido},
            </c:forEach>
        ];

        const ctx1 = document.getElementById('chartProductosVendidos').getContext('2d');
        new Chart(ctx1, {
            type: 'bar',
            data: {
                labels: nombres,
                datasets: [{
                    data: cantidades,
                    backgroundColor: ['#3B82F6', '#8B5CF6', '#10B981', '#F59E0B', '#EF4444'],
                    borderRadius: 6
                }]
            },
            options: {
                plugins: { legend: { display: false } },
                scales: { y: { beginAtZero: true } }
            }
        });

        // Ejemplo de gráfico de categorías
        const ctx2 = document.getElementById('chartCategorias').getContext('2d');
        new Chart(ctx2, {
            type: 'doughnut',
            data: {
                labels: ['Herramientas', 'Pinturas', 'Cemento', 'Electricidad'],
                datasets: [{
                    data: [40, 25, 20, 15],
                    backgroundColor: ['#3B82F6', '#10B981', '#F59E0B', '#EF4444']
                }]
            },
            options: { plugins: { legend: { position: 'bottom' } } }
        });
    </script>
</body>
</html>
