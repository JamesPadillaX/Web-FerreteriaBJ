<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Cliente, modelo.Producto, modelo.DetalleCarrito" %>
<%@ page import="dao.CarritoDAO" %>
<%@ page import="java.util.List" %>
<%@ include file="header.jsp" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");
    if (cliente == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String metodoEnvio = request.getParameter("metodoEnvio");
    String totalStr = request.getParameter("total");
    String idCarritoStr = request.getParameter("idCarrito");

    if (metodoEnvio == null || totalStr == null || idCarritoStr == null) {
        response.sendRedirect("pago.jsp?error=faltanParametros");
        return;
    }

    int idCarrito = Integer.parseInt(idCarritoStr);
    double total = Double.parseDouble(totalStr);

    CarritoDAO carritoDAO = new CarritoDAO();
    List<DetalleCarrito> detalles = carritoDAO.listarDetallePorCarrito(idCarrito);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Pagar con Yape</title>
    <link rel="stylesheet" href="WebContent/css/web/yape.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="layout">

    <main>
        <div class="contenedor-pago">
            <div class="columna-pago">
                <div class="qr-container">
                    <h2>Escanea el código QR</h2>
                    <img src="imagenes/pagos/yape.jpg" alt="Código QR de Yape" class="qr-img">
                    <p>Yapea al número: <strong>987 654 321</strong></p>
                </div>

                <form action="ConfirmarPagoYapeServlet" method="post" enctype="multipart/form-data" class="form-confirmacion">
                    <input type="hidden" name="idCarrito" value="<%= idCarrito %>">
                    <input type="hidden" name="total" value="<%= total %>">
                    <input type="hidden" name="metodoEnvio" value="<%= metodoEnvio %>">

                    <div class="campo-form">
                        <label for="codigoOperacion">Número de operación:</label>
                        <input type="text" id="codigoOperacion" name="codigoOperacion"
                               pattern="\d{8}" maxlength="8" minlength="8"
                               placeholder="" required
                               oninput="this.value=this.value.replace(/[^0-9]/g,'');">
                    </div>
                    
<div class="campo-form">
    <label for="imagenComprobante">Comprobante de pago:</label>
    <div class="custom-file-input">
        <input type="file" id="imagenComprobante" name="imagenComprobante" accept="image/*" required>
        <label for="imagenComprobante" id="fileLabel">
            <svg class="file-icon" xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 24 24">
                <path d="M5 20h14v-2H5v2zm7-18L6.5 7.5h4v6h3v-6h4L12 2z"/>
            </svg>
            <span>Seleccionar imagen</span>
        </label>
        <span id="fileName">Sin archivo seleccionado</span>
    </div>
</div>



                    <button type="submit" class="btn-confirmar">Confirmar el pago</button>
                </form>
            </div>

            <div class="columna-resumen">
                <h2>Resumen de compra</h2>
                <% for (DetalleCarrito dc : detalles) {
                    Producto p = dc.getProducto(); %>
                    <div class="resumen-linea">
                        <span title="<%= p.getNombre() %>"><%= p.getNombre() %> x<%= dc.getCantidad() %></span>
                        <span>S/. <%= String.format("%.2f", p.getPrecio() * dc.getCantidad()) %></span>
                    </div>
                <% } %>

                <div class="resumen-linea total">
                    <span>Total:</span>
                    <span>S/. <%= String.format("%.2f", total) %></span>
                </div>
            </div>
        </div>
    </main>

    <%@ include file="footer.jsp" %>
</div>

<script>
    const fileInput = document.getElementById('imagenComprobante');
    const fileName = document.getElementById('fileName');
    const fileLabel = document.getElementById('fileLabel');

    fileInput.addEventListener('change', function () {
        if (this.files.length > 0) {
            let name = this.files[0].name;
            fileName.textContent = name.length > 30 ? name.slice(0, 27) + '...' : name;
            fileLabel.classList.add("selected");
        } else {
            fileName.textContent = "Sin archivo seleccionado";
            fileLabel.classList.remove("selected");
        }
    });
</script>
</body>
</html>
