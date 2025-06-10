<%@ page import="java.util.List" %>
<%@ page import="dao.ProductoDAO" %>
<%@ page import="modelo.Producto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Productos</title>
  <link rel="stylesheet" href="WebContent/css/web/cagetoriaProductos.css">

</head>

<body class="pagina-productos">

  <%@ include file="header.jsp" %>

  <main>
    <%
      String idParam = request.getParameter("id");
      String orden = request.getParameter("orden");

      int idCategoria = Integer.parseInt(idParam);
      ProductoDAO dao = new ProductoDAO();
      List<Producto> productos = dao.listarProductosPorCategoriaWeb(idCategoria);

      if (orden != null) {
        switch (orden) {
          case "precio_asc":
            productos.sort((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()));
            break;
          case "precio_desc":
            productos.sort((p1, p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()));
            break;
          case "recomendado":
            // lógica futura
            break;
        }
      }
    %>

    <!-- Filtro de orden -->
    <form method="get" class="form-filtro">
      <input type="hidden" name="id" value="<%= idCategoria %>">
      <label for="orden">Ordenar por:</label>
      <select name="orden" id="orden" onchange="this.form.submit()">
        <option value="recomendado" <%= "recomendado".equals(orden) ? "selected" : "" %>>Recomendado</option>
        <option value="precio_asc" <%= "precio_asc".equals(orden) ? "selected" : "" %>>Precio: menor a mayor</option>
        <option value="precio_desc" <%= "precio_desc".equals(orden) ? "selected" : "" %>>Precio: mayor a menor</option>
      </select>
    </form>

    <!-- Productos -->

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
          <div class="card-producto">
            <div class="contenido">
              <div class="imagen-producto">
                <img src="<%= request.getContextPath() %>/<%= prod.getImagen() %>" alt="<%= prod.getNombre() %>">
              </div>
              <h3><%= prod.getNombre() %></h3>
              <p><%= prod.getDescripcion() %></p>
              <p><strong>Precio:</strong> S/. <%= prod.getPrecio() %></p>
            </div>
            <form action="AgregarAlCarritoServlet" method="post">
              <input type="hidden" name="idProducto" value="<%= prod.getIdProducto() %>"> 
              <input type="hidden" name="cantidad" value="1"> 
              <button type="submit" class="btn-agregar">Agregar 🛒</button>
            </form>
          </div>
          <% 
        }
    
      }
      %>
    </div>
  </main>
  <c:if test="${param.msg == 'agregado'}">
    <jsp:include page="WebContent/componentes/alertaAgregadoCarrito.jsp" />
  </c:if>
  <%@ include file="footer.jsp" %>
</div>
</body>
</html>

