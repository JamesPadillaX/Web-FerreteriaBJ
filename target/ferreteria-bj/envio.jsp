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

    // Obtener domicilio principal
    DomicilioClienteDAO domicilioDAO = new DomicilioClienteDAO();
    DomicilioCliente domicilioPrincipal = domicilioDAO.obtenerPrincipalPorCliente(cliente.getIdCliente());

    // Obtener detalles del carrito
    CarritoDAO carritoDAO = new CarritoDAO();
    int idCarrito = carritoDAO.obtenerCarritoActivo(cliente.getIdCliente());
    List<DetalleCarrito> detalles = carritoDAO.listarDetallePorCarrito(idCarrito);

    double total = 0.0;
    for (DetalleCarrito dc : detalles) {
        total += dc.getProducto().getPrecio() * dc.getCantidad();
    }

    boolean tieneDomicilio = (domicilioPrincipal != null);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Seleccionar envío</title>
    <link rel="stylesheet" href="WebContent/css/web/envio.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="layout">
    <%@ include file="header.jsp" %>

    <main>
        <form action="MetodosPago" method="post">
            <div class="contenedor-envio">
                <div class="columna-opciones">
                    <h1>Elige la forma de entrega</h1>

                    <label class="opcion-envio">
                        <input type="radio" name="metodoEnvio" value="DOMICILIO" <%= tieneDomicilio ? "" : "disabled" %> required>
                        <div class="info-envio">
                            <h2>Enviar a domicilio</h2>
                            <% if (tieneDomicilio) { %>
                                <p><%= domicilioPrincipal.getCalle() %> <%= domicilioPrincipal.getNumero() %>,
                                   <%= domicilioPrincipal.getDistrito() %>, <%= domicilioPrincipal.getProvincia() %> - <%= domicilioPrincipal.getDepartamento() %></p>
                            <% } else { %>
                                <p style="color: red;">No tienes un domicilio asignado.</p>
                            <% } %>
                            <a href="domicilio.jsp" class="modificar">Modificar domicilio o elegir otro</a>
                        </div>
                        <span class="gratis">Gratis</span>
                    </label>

                    <label class="opcion-envio">
                        <input type="radio" name="metodoEnvio" value="RECOJO" required>
                        <div class="info-envio">
                            <h2>Retirar en tienda</h2>
                        </div>
                        <span class="gratis">Gratis</span>
                    </label>
                </div>

                <div class="columna-resumen">
                    <h2>Resumen de compra</h2>
                    <div class="resumen-linea">
                        <span>Productos</span>
                        <span>S/. <%= String.format("%.2f", total) %></span>
                    </div>
                    <div class="resumen-linea">
                        <span><strong>Pagas</strong></span>
                        <span><strong>S/. <%= String.format("%.2f", total) %></strong></span>
                    </div>

                    <button type="submit" class="btn-continuar">
                        Continuar
                    </button>
                </div>
            </div>
        </form>
    </main>

    <%@ include file="footer.jsp" %>
</div>
</body>
</html>
