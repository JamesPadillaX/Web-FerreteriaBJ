<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, modelo.Categoria, dao.CategoriaDAO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    CategoriaDAO categoriaDAO = new CategoriaDAO();
    List<Categoria> listaCategoria = categoriaDAO.listarCategoriasActivas();
    request.setAttribute("categorias", listaCategoria);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Gestionar Productos</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link rel="stylesheet" href="WebContent/css/panel/gestionarProductos.css" />
    <link rel="stylesheet" href="WebContent/css/panel/modalEditarProducto.css" />
    <link rel="stylesheet" href="WebContent/css/panel/modalVerImagenes.css" />
</head>
<body>
<div class="container">
    <jsp:include page="WebContent/componentes/sidebar.jsp" />

    <div class="content">
        <h1>Gestión de Productos</h1>

        <div class="export-buttons" style="margin-bottom: 15px;">
            <a href="ExportarProductosPdfServlet?idCategoria=${param.idCategoria}" 
               class="btn-exportar btn-pdf" style="margin-right: 10px;">
                <i class="fas fa-file-pdf"></i> Exportar PDF
            </a>
        </div>

        <button id="btnAbrirModalProducto" class="btn-agregar">
            <i class="fas fa-plus"></i> Agregar Nuevo Producto
        </button>

        <div class="filtros-container">
            <form method="get" action="ListarProductosServlet" class="form-filtro" id="formFiltro">
                <div class="filtro-item">
                    <div class="buscador-wrapper">
                        <i class="fas fa-search"></i>
                        <input type="text" id="nombre" name="nombre" 
                               placeholder="Buscar producto..."  
                               value="${param.nombre != null ? param.nombre : ''}" />
                    </div>
                </div>

                <div class="filtro-item">
                    <select name="idCategoria" id="idCategoria">   
                        <option value="">-- Todas --</option>
                        <c:forEach var="cat" items="${categorias}">   
                            <option value="${cat.idCategoria}"
                                <c:if test="${param.idCategoria != null && param.idCategoria == cat.idCategoria.toString()}">selected</c:if>>
                                ${cat.nombre}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="filtro-item">
                    <button type="button" onclick="limpiarFiltros()" class="btn-limpiar">
                        <i class="fas fa-eraser"></i> Limpiar
                    </button>
                </div>
            </form>
        </div>
        <div class="cards-container">
            <c:forEach var="producto" items="${productos}">
            <div class="product-card ${producto.cantidad < 10 ? 'low-stock-card' : ''}"data-idproducto="${producto.idProducto}"
            data-nombre="${fn:toLowerCase(producto.nombre)}"
            data-categoria="${producto.idCategoria}">

       
            <c:choose>
                <c:when test="${producto.estado == 1}">
                    <span class="badge badge-activo">Activo</span>
                </c:when>
                <c:otherwise>
                    <span class="badge badge-inactivo">Inactivo</span>
                </c:otherwise>
            </c:choose>

   
            <div class="card-img">
                <c:choose>
                    <c:when test="${not empty producto.imagen}">
                        <img src="${pageContext.request.contextPath}/${producto.imagen}"
                             alt="${producto.nombre}">
                    </c:when>
                    <c:otherwise>
                        <span class="no-img">Sin imagen</span>
                    </c:otherwise>
                </c:choose>
            </div>

  
            <div class="card-body">
                <h3 class="card-title">${producto.nombre}</h3>
                <p class="card-category">${producto.categoria}</p>

   
                <p class="card-price">S/. ${producto.precio}</p>
                
                <p class="card-stock">
                    Stock: ${producto.cantidad}
                </p>

            </div>


            <div class="card-actions">
                <button class="action-btn add-images"
                        data-id="${producto.idProducto}"
                        data-categoria="${producto.nombre}">
                    <i class="fas fa-plus"></i>
                </button>

                <button class="action-btn btnVerImagenes view-images"
                        data-nombre="${producto.nombre}"
                        data-imagenes='
                        <c:out value="["/><c:forEach var="img" items="${producto.imagenes}" varStatus="st">
                            "<c:out value='${pageContext.request.contextPath}/${fn:escapeXml(img.rutaImagen)}'/>"<c:if test='${!st.last}'>,</c:if>
                        </c:forEach><c:out value="]"/>'
                >
                    <i class="fas fa-images"></i>
                </button>

                <button class="action-btn btnEditarProducto"
                        data-id="${producto.idProducto}"
                        data-nombre="${producto.nombre}"
                        data-descripcion="${producto.descripcion}"
                        data-precio="${producto.precio}"
                        data-cantidad="${producto.cantidad}"
                        data-idcategoria="${producto.idCategoria}"
                        data-estado="${producto.estado}"
                        data-imagen="${producto.imagen}">
                    <i class="fas fa-edit"></i>
                </button>

                <form action="EliminarProductoServlet" method="get" class="form-eliminar">
                    <input type="hidden" name="idProducto" value="${producto.idProducto}" />
                    <button class="action-btn delete" type="submit">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </form>
            </div>
        </div>
    </c:forEach>


    <c:if test="${empty productos}">
        <p class="no-products">No se encontraron productos.</p>
    </c:if>
</div>

<jsp:include page="WebContent/componentes/modalRegistroProducto.jsp" />
<jsp:include page="WebContent/componentes/modalEditarProducto.jsp" />
<jsp:include page="WebContent/componentes/modalAgregarImagenes.jsp" />


<div id="modalVerImagenes" class="modal-overlay" style="display: none;">
    <div class="modal-content-ver">
        <span class="close-modal" id="btnCerrarModalVerImagenes">
            <i class="fas fa-times-circle"></i>
        </span>
        <h2>Imágenes del Producto</h2>
        <p id="nombreProductoVer" style="text-align: center; margin-bottom: 10px; color: #555;"></p>
        <div class="imagenes-grid" id="contenedorImagenes"></div>
        <div style="text-align: center; margin-top: 20px;">
            <button type="button" class="btn-cerrar" id="btnCancelarVerImagenes">
                <i class="fas fa-times-circle"></i> Cancelar
            </button>
        </div>
    </div>
</div>


<script src="WebContent/js/panel/modalAgregarProduto.js"></script>
<script src="WebContent/js/panel/eliminarProducto.js"></script>
<script src="WebContent/js/panel/modalEditarProducto.js"></script>
<script src="WebContent/js/panel/verImagenes.js"></script>
<script src="WebContent/js/panel/buscarProducto.js"></script>


<c:if test="${param.msg == 'exito'}"><jsp:include page="WebContent/componentes/alerta.jsp" /></c:if>
<c:if test="${param.msg == 'eliminado'}"><jsp:include page="WebContent/componentes/alertaEliminado.jsp" /></c:if>
<c:if test="${param.msg == 'editado'}"><jsp:include page="WebContent/componentes/alertaEditado.jsp" /></c:if>

</body>
</html>