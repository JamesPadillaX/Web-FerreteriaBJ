<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Producto" %>
<%@ page import="modelo.ImagenProducto" %>
<%@ page import="java.util.List" %>
<%
    Producto prod = (Producto) request.getAttribute("producto");
    List<Producto> relacionados = (List<Producto>) request.getAttribute("productosRelacionados");
    if (prod == null) {
%>
    <p>Producto no disponible.</p>
<%
    return;
}
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title><%= prod.getNombre() %> - Detalle</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="WebContent/css/web/detalleProducto.css" />
    <link rel="stylesheet" href="WebContent/css/web/carruselImagenes.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body class="pagina-productos">
<%@ include file="header.jsp" %>

<main class="contenedor-productos">
    <div class="detalle-producto">
        <div class="detalle-imagen">
            <div class="carrusel-container">
                <div class="vertical-carrusel">
                    <button class="flecha-miniatura" id="flechaArriba"><i class="fas fa-chevron-up"></i></button>
                    <div class="galeria-carrusel" id="contenedorMiniaturas">
                        <img src="<%= request.getContextPath() + "/" + prod.getImagen() %>" class="miniatura activa" onclick="cambiarImagenCarrusel(this)" />
                        <% 
                            List<ImagenProducto> imagenesSecundarias = prod.getImagenes();
                            if (imagenesSecundarias != null && !imagenesSecundarias.isEmpty()) {
                                for (ImagenProducto img : imagenesSecundarias) {
                        %>
                            <img src="<%= request.getContextPath() + "/" + img.getRutaImagen() %>" class="miniatura" onclick="cambiarImagenCarrusel(this)" />
                        <% 
                                }
                            } 
                        %>
                    </div>
                    <button class="flecha-miniatura" id="flechaAbajo"><i class="fas fa-chevron-down"></i></button>
                </div>
                <div class="imagen-principal">
                    <img id="imagenCarrusel" src="<%= request.getContextPath() + "/" + prod.getImagen() %>" alt="<%= prod.getNombre() %>" />
                </div>
            </div>
        </div>

        <div class="detalle-info">
            <h2 class="nombre-prod"><%= prod.getNombre() %></h2>
            <p class="descripcion-prod"><%= prod.getDescripcion() %></p>
            <p class="precio-prod">S/ <%= prod.getPrecio() %></p>

            <form action="AgregarAlCarritoServlet" method="post" class="form-cantidad">
                <input type="hidden" name="idProducto" value="<%= prod.getIdProducto() %>" />
                <div class="selector-cantidad">
                    <button type="button" class="btn-cantidad" onclick="modificarCantidad(-1)">−</button>
                    <input type="number" name="cantidad" id="cantidad" value="1" min="1" readonly />
                    <button type="button" class="btn-cantidad" onclick="modificarCantidad(1)">+</button>
                </div>
                <button type="submit" class="btn-agregar">
                    <i class="fas fa-cart-plus"></i> Agregar al carrito
                </button>
            </form>
            <a href="productosPorCategoria.jsp?id=<%= prod.getIdCategoria() %>" class="btn-detalle">
                <i class="fas fa-arrow-left"></i> Volver a la categoría
            </a>
        </div>
    </div>

    <!-- Productos relacionados -->
    <div class="relacionados">
        <h3>También te puede interesar</h3>
        <div class="contenedor-flechas">
            <button class="btn-flecha" id="btnIzquierda"><i class="fas fa-chevron-left"></i></button>
            <div class="lista-relacionados" id="listaRelacionados">
                <% if (relacionados != null) {
                    for (Producto p : relacionados) {
                        if (p.getIdProducto() != prod.getIdProducto()) {
                %>
                <div class="card-relacionado">
                    <a href="DetalleProductoServlet?id=<%= p.getIdProducto() %>" class="relacionado-link">
                        <div class="contenido-relacionado">
                            <img src="<%= request.getContextPath() + "/" + p.getImagen() %>" alt="<%= p.getNombre() %>" />
                            <h4><%= p.getNombre() %></h4>
                            <p class="precio-relacionado">S/ <%= p.getPrecio() %></p>
                            <p class="desc-relacionado"><%= p.getDescripcion() %></p>
                        </div>
                    </a>
                </div>
                <% } } } %>
            </div>
            <button class="btn-flecha" id="btnDerecha"><i class="fas fa-chevron-right"></i></button>
        </div>
    </div>
</main>

<%@ include file="footer.jsp" %>

<script>
function cambiarImagenCarrusel(img) {
    const principal = document.getElementById("imagenCarrusel");
    const nuevaSrc = img.src;

    const temp = new Image();
    temp.onload = function () {
        principal.src = nuevaSrc;
        document.querySelectorAll(".miniatura").forEach(i => i.classList.remove("activa"));
        img.classList.add("activa");
    };
    temp.src = nuevaSrc;
}


    function modificarCantidad(cambio) {
        const input = document.getElementById("cantidad");
        let valor = parseInt(input.value) + cambio;
        if (valor < 1) valor = 1;
        input.value = valor;
    }

    // Scroll flechas productos relacionados
    document.getElementById('btnIzquierda').addEventListener('click', () => {
        document.getElementById('listaRelacionados').scrollBy({ left: -220, behavior: 'smooth' });
    });
    document.getElementById('btnDerecha').addEventListener('click', () => {
        document.getElementById('listaRelacionados').scrollBy({ left: 220, behavior: 'smooth' });
    });

</script>
</body>
</html>
