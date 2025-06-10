<%@ page import="java.util.List" %>
<%@ page import="modelo.DetalleCarrito" %>
<%@ page import="modelo.Producto" %>
<%@ page import="modelo.Cliente" %>
<%@ page import="dao.CarritoDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");
    if (cliente == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    CarritoDAO carritoDAO = new CarritoDAO();
    int idCarrito = carritoDAO.obtenerCarritoActivo(cliente.getIdCliente());
    List<DetalleCarrito> detalles = carritoDAO.listarDetallePorCarrito(idCarrito);
    double total = 0.0;
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Carrito de Compras</title>
    <link rel="stylesheet" href="WebContent/css/web/carrito.css">
</head>
<body>
<div class="layout">

    <%@ include file="header.jsp" %>

    <main class="contenido-principal">
        <div class="contenedor-carrito">
            <div class="columna-productos">
                <h1><i class="fas fa-shopping-cart"></i> Carrito de compras</h1>

                <% if (detalles == null || detalles.isEmpty()) { %>
                    <p class="empty">Tu carrito está vacío.</p>
                <% } else {
                    for (DetalleCarrito dc : detalles) {
                        Producto p = dc.getProducto();
                        double subtotal = p.getPrecio() * dc.getCantidad();
                        total += subtotal;
                %>
                <div class="producto-card">
                    <div class="producto-img">
                        <img src="<%=request.getContextPath()%>/<%=p.getImagen()%>" alt="Producto">
                    </div>
                    <div class="producto-info">
                        <h2><%=p.getNombre()%></h2>
                        <p><strong>Precio Unitario:</strong> S/. <%=p.getPrecio()%></p>
                        <p><strong>Cantidad:</strong> <%=dc.getCantidad()%></p>
                        <p><strong>Subtotal:</strong> S/. <%=subtotal%></p>
                        <form method="post" action="EliminarDelCarritoServlet">
                            <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>">
                            <button type="submit" class="btn-eliminar">Eliminar</button>
                        </form>
                    </div>
                </div>
                <% }} %>
            </div>

            <div class="columna-resumen">
                <h2>Resumen de compra</h2>
                <p>Total de productos: <strong><%=detalles.size()%></strong></p>
                <hr>
                <div class="detalle-productos">
                    <% for (DetalleCarrito dc : detalles) { %>
                        <div class="linea-producto">
                            <span><%=dc.getProducto().getNombre()%></span>
                            <span>S/. <%=dc.getProducto().getPrecio() * dc.getCantidad()%></span>
                        </div>
                    <% } %>
                </div>
                <hr>
                <div class="total">
                    <strong>Total:</strong>
                    <strong>S/. <%=String.format("%.2f", total)%></strong>
                </div>
                <form action="FinalizarCompraServlet" method="post">
                    <button type="submit" class="btn-comprar">Comprar</button>
                </form>
                <a href="productos.jsp" class="btn-volver">← Ver más productos</a>
            </div>
        </div>
    </main>

    <c:if test="${param.msg == 'eliminado'}">
        <jsp:include page="WebContent/componentes/alertaEliminadoCarrito.jsp" />
    </c:if>

    <%@ include file="footer.jsp" %>
</div>
</body>
</html>
