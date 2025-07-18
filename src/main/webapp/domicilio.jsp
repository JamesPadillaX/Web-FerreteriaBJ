<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Cliente" %>
<%@ page import="modelo.DomicilioCliente" %>
<%@ page import="modelo.DetalleCarrito" %>
<%@ page import="dao.DomicilioClienteDAO" %>
<%@ page import="dao.CarritoDAO" %>
<%@ page import="java.util.List" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");
    if (cliente == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    DomicilioClienteDAO domicilioDAO = new DomicilioClienteDAO();
    List<DomicilioCliente> domicilios = domicilioDAO.listarPorCliente(cliente.getIdCliente());

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
    <title>Mis domicilios</title>
    <link rel="stylesheet" href="WebContent/css/web/domicilio.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="layout">
    <%@ include file="header.jsp" %>

    <main>
        <div class="contenedor-envio">
            <div class="columna-opciones">
                <h1>Mis domicilios</h1>
                <form action="MarcarPrincipalServlet" method="post">
                    <% if (domicilios.isEmpty()) { %>
                        <p class="sin-domicilios">No tienes domicilios registrados aún.</p>
                        <a href="formDomicilio.jsp" class="btn-agregar-rojo">Agregar domicilio</a>
                    <% } else {
                        for (DomicilioCliente d : domicilios) { %>
                            <label class="domicilio-card <%= d.isPrincipal() ? "principal" : "" %>">
                                <input type="radio" name="idDomicilioPrincipal"
                                       value="<%= d.getIdDomicilio() %>" <%= d.isPrincipal() ? "checked" : "" %> required>

                                <div class="domicilio-info">
                                    <p><strong>Calle:</strong> <%= d.getCalle() %> <%= d.getNumero() %></p>
                                    <p><strong>Distrito:</strong> <%= d.getDistrito() %>, <%= d.getProvincia() %> - <%= d.getDepartamento() %></p>
                                    <p><strong>Referencia:</strong> <%= d.getReferencia() %></p>
                                </div>

                                <div class="domicilio-derecha">
                                    <% if (d.isPrincipal()) { %>
                                        <span class="badge-principal">Principal</span>
                                    <% } %>
                                </div>
                            </label>
                    <%  } %>

                    <div class="botones-acciones">
                        <button type="submit" class="btn-base">Guardar como domicilio principal</button>
                        <a href="formDomicilio.jsp" class="btn-base">Agregar nuevo domicilio</a>
                    </div>

                    <% } %>
                </form>
            </div>

            <div class="columna-resumen">
                <h2>Resumen de compra</h2>
                <div class="resumen-linea">
                    <span>Productos</span>
                    <span>S/. <%= String.format("%.2f", total) %></span>
                </div>
                <div class="resumen-linea">
                    <span><strong>Total a pagar</strong></span>
                    <span><strong>S/. <%= String.format("%.2f", total) %></strong></span> 
                </div>
                <form action="envio.jsp" method="get">
                    <button type="submit" class="btn-continuar">Volver a método de envío</button>
                </form>
            </div>
        </div>
    </main>

    <%@ include file="footer.jsp" %>
</div>
</body>
</html>