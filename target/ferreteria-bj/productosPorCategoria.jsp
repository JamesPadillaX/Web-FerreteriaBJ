<%@ page import="java.util.List" %>
<%@ page import="dao.ProductoDAO" %>
<%@ page import="dao.CategoriaDAO" %>
<%@ page import="modelo.Producto" %>
<%@ page import="modelo.Categoria" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Productos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="WebContent/css/web/cagetoriaProductos.css" />
</head>
<body class="pagina-productos">
<%@ include file="header.jsp" %>

<main class="contenedor-productos">
    <%
        String idParam = request.getParameter("id");
        String orden = request.getParameter("orden");

        int idCategoria = 0;
        try {
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new NumberFormatException("ID no proporcionado");
            }
            idCategoria = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            out.println("<p style='color:red; font-weight:bold;'>⚠️ Error: Categoría no válida.</p>");
            return;
        }

        ProductoDAO dao = new ProductoDAO();
        List<Producto> productos = dao.listarProductosPorCategoriaWeb(idCategoria);

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Categoria categoria = categoriaDAO.obtenerCategoriaPorId(idCategoria);
        String nombreCategoria = (categoria != null) ? categoria.getNombre() : "Categoría";

        if (orden != null) {
            switch (orden) {
                case "precio_asc":
                    productos.sort((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()));
                    break;
                case "precio_desc":
                    productos.sort((p1, p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()));
                    break;
            }
        }
    %>

    <div class="encabezado-productos">
        <h1 class="titulo-categoria"><%= nombreCategoria %></h1>

        <div class="barra-superior">
            <form method="get" class="form-orden">
                <input type="hidden" name="id" value="<%= idCategoria %>" />
                <label for="orden">Ordenar por</label>
                <select name="orden" id="orden" onchange="this.form.submit()">
                    <option value="recomendado" <%= "recomendado".equals(orden) || orden == null ? "selected" : "" %>>Más relevantes</option>
                    <option value="precio_asc" <%= "precio_asc".equals(orden) ? "selected" : "" %>>Menor precio</option>
                    <option value="precio_desc" <%= "precio_desc".equals(orden) ? "selected" : "" %>>Mayor precio</option>
                </select>
            </form>
        </div>
    </div>

    <div class="grid-productos">
        <%
            if (productos == null || productos.isEmpty()) {
        %>
            <div class="mensaje-sin-productos">
                <p>No hay productos disponibles en esta categoría.</p>
            </div>
        <%
            } else {
                for (Producto prod : productos) {
        %>
            <div class="tarjeta-producto">
                <img class="img-prod" src="<%= request.getContextPath() %>/<%= prod.getImagen() %>" alt="<%= prod.getNombre() %>" />
                <div class="cuerpo-tarjeta">
                    <h3 class="nombre-prod"><%= prod.getNombre() %></h3>
                    <p class="descripcion-prod"><%= prod.getDescripcion() %></p>
                    <p class="precio-prod">S/ <%= prod.getPrecio() %></p>
                    <div class="acciones-prod">
                        <form action="AgregarAlCarritoServlet" method="post">
                            <input type="hidden" name="idProducto" value="<%= prod.getIdProducto() %>" />
                            <input type="hidden" name="cantidad" value="1" />
                            <button type="submit" class="btn-agregar" title="Agregar al carrito">
                                <i class="fas fa-cart-plus"></i>
                            </button>
                        </form>
                        <form action="DetalleProductoServlet" method="get">
                            <input type="hidden" name="id" value="<%= prod.getIdProducto() %>" />
                            <button type="submit" class="btn-detalle" title="Ver detalles">
                                <i class="fas fa-eye"></i>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        <%
                }
            }
        %>
    </div>
</main>
<%@ include file="footer.jsp" %>
</body>
</html>
