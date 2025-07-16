<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Ventas</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="WebContent/css/panel/gestionarVentas.css">
</head>
<body>
<div class="container">
    <jsp:include page="WebContent/componentes/sidebar.jsp" />

    <div class="content">
        <h1>Gestión de Ventas</h1>
        <div class="contenedor-acciones">
            <form action="Ventas" method="get" class="filtro-container">
                <div>
                    <label for="fechaInicio">Desde</label>
                    <input type="date" name="fechaInicio" id="fechaInicio" value="${param.fechaInicio}">
                </div>
                <div>
                    <label for="fechaFin">Hasta</label>
                    <input type="date" name="fechaFin" id="fechaFin" value="${param.fechaFin}">
                </div>
                <button type="submit" class="btn-filtrar">
                    <i class="fas fa-filter"></i> Filtrar
                </button>
                <a href="Ventas" class="btn-limpiar">
                    <i class="fas fa-eraser"></i> Limpiar
                </a>
            </form>

            <form action="ExportarVentasPdfServlet" method="get" class="form-exportar">
                <input type="hidden" name="fechaInicio" value="${param.fechaInicio}">
                <input type="hidden" name="fechaFin" value="${param.fechaFin}">
                <button type="submit" class="btn-exportar-pdf">
                    <i class="fas fa-file-pdf"></i> Exportar PDF
                </button>
            </form>
        </div>

        <div class="tabla-ventas">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Cliente</th>
                        <th>Método Pago</th>
                        <th>Envío</th>
                        <th>Total</th>
                        <th>Fecha</th>
                        <th>Estado</th>
                        <th>Código Operación</th>
                        <th>Comprobante</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="v" items="${listaVentas}">
                        <tr>
                            <td>${v.idVenta}</td>
                            <td>${v.nombre}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${fn:toLowerCase(v.nombreMetodoPago) == 'yape'}">
                                        <span class="metodo-yape">${v.nombreMetodoPago}</span>
                                    </c:when>
                                    <c:when test="${fn:toLowerCase(v.nombreMetodoPago) == 'plin'}">
                                        <span class="metodo-plin">${v.nombreMetodoPago}</span>
                                    </c:when>
                                    <c:when test="${fn:toLowerCase(v.nombreMetodoPago) == 'bcp'}">
                                        <span class="metodo-bcp">${v.nombreMetodoPago}</span>
                                    </c:when>
                                    <c:otherwise>
                                        ${v.nombreMetodoPago}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${v.metodoEnvio}</td>
                            <td>S/. ${v.total}</td>
                            <td>${v.fecha}</td>
                            <td>
                                <span class="estado-label estado-${fn:toLowerCase(v.estado)}">${v.estado}</span>
                                <button class="btn-abrir-modal" onclick="abrirModalEstado('${v.idVenta}', '${v.estado}')">
                                    <i class="fas fa-edit"></i>
                                </button>
                            </td>
                            <td>${v.codigoOperacion}</td>
                            <td>
                                <c:if test="${not empty v.comprobante}">
                                    <a href="${pageContext.request.contextPath}/${v.comprobante}" target="_blank" class="btn-ver-comprobante" title="Ver comprobante">
                                        <i class="fas fa-file-invoice"></i>
                                    </a>
                                </c:if>
                                <c:if test="${empty v.comprobante}">
                                    <span class="sin-comprobante">-</span>
                                </c:if>
                            </td>
                            <td>
                                <a href="DetalleVentaServlet?idVenta=${v.idVenta}" class="btn-ver-detalle" title="Ver Detalle">
                                    <i class="fa-solid fa-magnifying-glass"></i> 
                                </a>

                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty listaVentas}">
                        <tr>
                            <td colspan="100%" class="fila-vacia">No hay ventas o datos disponibles</td>
                        </tr>
                    </c:if>

                </tbody>
            </table>
        </div>
    </div>
</div>


<div id="modalEstado" class="modal">
    <div class="modal-content">
        <h2>Cambiar Estado</h2>
        <form id="formActualizarEstado" action="ActualizarEstadoVentaServlet" method="post">
            <input type="hidden" name="idVenta" id="modal-idVenta">
            <label for="modal-estado">Selecciona nuevo estado:</label>
            <select name="estado" id="modal-estado" class="select-estado">
                <option value="PENDIENTE">PENDIENTE</option>
                <option value="PAGADO">PAGADO</option>
                <option value="ENVIADO">ENVIADO</option>
                <option value="ENTREGADO">ENTREGADO</option>
                <option value="CANCELADO">CANCELADO</option>
            </select>
            <div class="modal-actions">
                <button type="button" onclick="cerrarModal()" class="btn-cancelar">Cancelar</button>
                <button type="submit" class="btn-confirmar">Actualizar</button>
            </div>
        </form>
    </div>
</div>

<script>
    function abrirModalEstado(idVenta, estadoActual) {
        document.getElementById("modal-idVenta").value = idVenta;
        document.getElementById("modal-estado").value = estadoActual;
        document.getElementById("modalEstado").style.display = "flex";
    }

    function cerrarModal() {
        document.getElementById("modalEstado").style.display = "none";
    }

    window.addEventListener("click", function(e) {
        const modal = document.getElementById("modalEstado");
        if (e.target === modal) {
            cerrarModal();
        }
    });
</script>
</body>
</html>
