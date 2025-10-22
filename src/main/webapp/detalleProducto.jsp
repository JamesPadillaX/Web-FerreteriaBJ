<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>${producto.nombre} - Detalle</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="WebContent/css/web/detalleProducto.css" />
    <link rel="stylesheet" href="WebContent/css/web/carruselImagenes.css" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />

</head>
<body class="pagina-productos">
<main class="contenedor-productos">
    <div class="breadcrumb">
        <a href="inicio">Inicio</a> /
        <a href="ListarProductosPorCategoriaServlet?id=${producto.idCategoria}">${nombreCategoria}</a> /
        <span>${producto.nombre}</span>
    </div>

    <div class="detalle-producto">
        <div class="detalle-imagen">
            <div class="carrusel-container" style="display: flex; flex-direction: row; gap: 16px; align-items: flex-start;">
                <div class="vertical-carrusel">
            <button class="flecha-miniatura" id="flechaArriba"><i class="fas fa-chevron-up"></i></button>
            <div class="galeria-carrusel" id="contenedorMiniaturas">
                <img src="${pageContext.request.contextPath}/${producto.imagen}" 
                     class="miniatura activa"
                     onclick="cambiarImagenCarrusel(this)"
                     onmouseover="cambiarImagenCarrusel(this)" />

                <c:forEach var="img" items="${producto.imagenes}">
                    <img src="${pageContext.request.contextPath}/${img.rutaImagen}" 
                         class="miniatura"
                         onclick="cambiarImagenCarrusel(this)"
                         onmouseover="cambiarImagenCarrusel(this)" />
                </c:forEach>
            </div>
            <button class="flecha-miniatura" id="flechaAbajo"><i class="fas fa-chevron-down"></i></button>
        </div>

        <div class="imagen-principal">
            <img id="imagenCarrusel" src="${pageContext.request.contextPath}/${producto.imagen}" alt="${producto.nombre}" />
        </div>
    </div>
</div>

        <div class="detalle-info">
            <h2 class="nombre-prod">${producto.nombre}</h2>
            <p class="descripcion-prod">${producto.descripcion}</p>
            <p class="precio-prod">S/ ${producto.precio}</p>

            <form action="AgregarAlCarritoServlet" method="post" class="form-cantidad">
                <input type="hidden" name="idProducto" value="${producto.idProducto}" />
                <div class="selector-cantidad">
                    <button type="button" class="btn-cantidad" onclick="modificarCantidad(-1)">−</button>
                    <input type="number" name="cantidad" id="cantidad" value="1" min="1" readonly />
                    <button type="button" class="btn-cantidad" onclick="modificarCantidad(1)">+</button>
                </div>
                <button type="submit" class="btn-agregar">
                    <i class="fa-solid fa-cart-shopping"></i>Agregar al carrito
                </button>
            </form>
            <a href="ListarProductosPorCategoriaServlet?id=${producto.idCategoria}" class="btn-detalle">
                <i class="fas fa-arrow-left"></i> Volver
            </a>
        </div>
    </div>

    <div class="relacionados">
        <h3>También te puede interesar</h3>
        <div class="contenedor-flechas">
            <button class="btn-flecha" id="btnIzquierda"><i class="fas fa-chevron-left"></i></button>
            <div class="lista-relacionados" id="listaRelacionados">
                <c:forEach var="p" items="${productosRelacionados}">
                    <div class="card-relacionado">
                        <a href="DetalleProducto?id=${p.idProducto}" class="relacionado-link">
                            <div class="contenido-relacionado">
                                <img src="${pageContext.request.contextPath}/${p.imagen}" alt="${p.nombre}" />
                                <h4>${p.nombre}</h4>
                                <p class="precio-relacionado">S/ ${p.precio}</p>
                                <p class="desc-relacionado">${p.descripcion}</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
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

    document.getElementById('btnIzquierda').addEventListener('click', () => {
        document.getElementById('listaRelacionados').scrollBy({ left: -220, behavior: 'smooth' });
    });
    document.getElementById('btnDerecha').addEventListener('click', () => {
        document.getElementById('listaRelacionados').scrollBy({ left: 220, behavior: 'smooth' });
    });
</script>
<%@ include file="asistente.jsp" %>
</body>
</html>
