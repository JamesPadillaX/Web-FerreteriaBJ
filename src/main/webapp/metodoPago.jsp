<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Cliente" %>
<%@ page import="modelo.Producto" %>
<%@ page import="modelo.DetalleCarrito" %>
<%@ page import="dao.CarritoDAO" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");
    if (cliente == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String metodoEnvio = request.getParameter("metodoEnvio");
    if (metodoEnvio == null) {
        response.sendRedirect("envio.jsp");
        return;
    }

    CarritoDAO carritoDAO = new CarritoDAO();
    int idCarrito = carritoDAO.obtenerCarritoActivo(cliente.getIdCliente());
    List<DetalleCarrito> detalles = carritoDAO.listarDetallePorCarrito(idCarrito);

    double total = 0.0;
    for (DetalleCarrito dc : detalles) {
        total += dc.getProducto().getPrecio() * dc.getCantidad();
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Seleccionar método de pago</title>
    <link rel="stylesheet" href="WebContent/css/web/pago.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="layout">

    <main>
        <form action="SeleccionarMetodoPagoServlet" method="post">
            <input type="hidden" name="metodoEnvio" value="<%= metodoEnvio %>">
            <input type="hidden" name="total" value="<%= total %>">
            <input type="hidden" name="idCarrito" value="<%= idCarrito %>">

            <div class="contenedor-pago-envio">
                <div class="columna-opciones">
                    <h1>Elige la forma de pago</h1>

                    <c:forEach var="m" items="${metodosPago}">
                        <label class="opcion-envio">
                            <input type="radio" name="idMetodoPago" value="${m.idMetodoPago}" required>
                            <div class="info-envio">
                                <h2>${m.nombre}</h2>
                                <p>${m.descripcion}</p>
                                <c:if test="${not empty m.imagen}">
                                    <img src="${m.imagen}" alt="${m.nombre}" class="icono-pago">
                                </c:if>
                            </div>
                        </label>
                    </c:forEach>

                    <button type="submit" class="btn-continuar">Continuar</button>
                </div>

                <div class="columna-resumen">
                    <h2>Resumen de compra</h2>

                    <% if (detalles != null) {
                        for (DetalleCarrito dc : detalles) {
                            Producto p = dc.getProducto();
                    %>
                    <div class="resumen-linea">
                        <span title="<%= p.getNombre() %>"><%= p.getNombre() %> x<%= dc.getCantidad() %></span>
                        <span>S/. <%= String.format("%.2f", p.getPrecio() * dc.getCantidad()) %></span>
                    </div>
                    <% }} %>

                    <div class="resumen-linea" style="font-weight: bold; border-top: 1px solid #ccc; margin-top: 10px; padding-top: 10px;">
                        <span>Total a pagar:</span>
                        <span>S/. <%= String.format("%.2f", total) %></span>
                    </div>
                </div>
            </div>
        </form>
    </main>

    <%@ include file="footer.jsp" %>
</div>
</body>
</html>
