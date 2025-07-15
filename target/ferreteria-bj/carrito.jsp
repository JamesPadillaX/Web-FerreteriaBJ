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
    boolean carritoVacio = (detalles == null || detalles.isEmpty());
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Carrito de Compras</title>
    <link rel="stylesheet" href="WebContent/css/web/carrito.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        .columna-resumen.deshabilitado {
            opacity: 0.6;
            pointer-events: none;
        }

        .columna-resumen.deshabilitado .btn-comprar {
            background-color: #ccc !important;
            color: #888 !important;
            cursor: not-allowed !important;
        }
    </style>
</head>
<body>
<div class="layout">
    <%@ include file="header.jsp" %>

    <main class="contenido-principal">
        <div class="contenedor-carrito">
            <div class="columna-productos">
                <h1><i class="fas fa-shopping-cart"></i> Carrito de compras</h1>

                <% if (carritoVacio) { %>
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
                        <div class="producto-header">
                            <h2><%=p.getNombre()%></h2>
                            <form method="post" action="ActualizarCantidadServlet" class="control-cantidad">
                                <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>">
                                <button type="submit" name="accion" value="restar" <% if(dc.getCantidad() <= 1){ %>disabled<% } %>>−</button>
                                <input type="text" name="cantidad" value="<%=dc.getCantidad()%>" readonly>
                                <button type="submit" name="accion" value="sumar">+</button>
                            </form>
                            <span class="producto-subtotal">S/. <%=String.format("%.2f", subtotal)%></span>
                        </div>
                        <p><strong>Precio Unitario:</strong> S/. <%=p.getPrecio()%></p>
                        <form method="post" action="EliminarDelCarritoServlet">
                            <input type="hidden" name="idProducto" value="<%=p.getIdProducto()%>">
                            <button type="submit" class="btn-eliminar">Eliminar</button>
                        </form>
                    </div>
                </div>
                <% }} %>
            </div>

            <!-- Resumen siempre visible pero desactivado si carrito está vacío -->
            <div class="columna-resumen <%= carritoVacio ? "deshabilitado" : "" %>">
                <h2>Resumen de compra</h2>

                <% if (carritoVacio) { %>
                    <p>No hay productos en el carrito.</p>
                    <hr>
                    <div class="detalle-productos">
                        <div class="linea-producto">
                            <span>—</span><span>—</span>
                        </div>
                    </div>
                    <hr>
                    <div class="total">
                        <strong>Total:</strong>
                        <strong>S/. 0.00</strong>
                    </div>
                    <button type="button" class="btn-comprar" disabled>Continuar compra</button>
                <% } else { %>
                    <p>Total de productos: <strong><%=detalles.size()%></strong></p>
                    <hr>
                    <div class="detalle-productos">
                        <% for (DetalleCarrito dc : detalles) { %>
                            <div class="linea-producto">
                                <span><%=dc.getProducto().getNombre()%></span>
                                <span>S/. <%=String.format("%.2f", dc.getProducto().getPrecio() * dc.getCantidad())%></span>
                            </div>
                        <% } %>
                    </div>
                    <hr>
                    <div class="total">
                        <strong>Total:</strong>
                        <strong>S/. <%=String.format("%.2f", total)%></strong>
                    </div>
                    <form action="envio.jsp" method="post">
                        <button type="submit" class="btn-comprar">Continuar compra</button>
                    </form>
                <% } %>
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
