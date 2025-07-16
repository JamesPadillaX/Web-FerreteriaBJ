<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Ventas ${año}</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="WebContent/css/panel/reporteVentas.css">
</head>
<body>
<div class="container">

    <jsp:include page="WebContent/componentes/sidebar.jsp" />

    <div class="content">
        <h1>📊 Reporte de Ventas Año ${año}</h1>

        <div class="filtro-container">

            <form action="ReporteVentas" method="get">
                <label for="anio">Selecciona año:</label>
                <select name="año" id="anio" class="select-anio">
                    <c:forEach var="i" begin="2020" end="2100">
                        <option value="${i}" ${i == año ? 'selected' : ''}>${i}</option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn-filtrar">
                    <i class="fas fa-chart-bar"></i> Ver Reporte
                </button>
            </form>

            <form action="ExportarReporteVentas" method="get">
                <input type="hidden" name="año" value="${año}">
                <button type="submit" class="btn-exportar">
                    <i class="fas fa-file-pdf"></i> Exportar PDF
                </button>
            </form>
        </div>

        <div class="grafico-container">
            <canvas id="graficaVentas"></canvas>
        </div>

    </div>
</div>


<script>
    const meses = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                   "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

    let datosVentas = new Array(12).fill(0);

    <c:forEach var="registro" items="${datosVentas}">
        datosVentas[${registro[0] - 1}] = ${registro[1]};
    </c:forEach>

    const ctx = document.getElementById('graficaVentas').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: meses,
            datasets: [{
                label: 'Ventas realizadas',
                data: datosVentas,
                backgroundColor: '#4A90E2',
                borderColor: '#357ABD',
                borderWidth: 1,
                borderRadius: 8
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    stepSize: 1
                }
            }
        }
    });
</script>

</body>
</html>
