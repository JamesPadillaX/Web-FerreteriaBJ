<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Gestionar Carrusel</title>
    <link rel="stylesheet" href="WebContent/css/panel/gestionarCarrusel.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>
<div class="container">
    <jsp:include page="WebContent/componentes/sidebar.jsp" />
    <div class="content">
        <h1>Gestión del Carrusel</h1>
        <button id="btnAbrirModalCarrusel" class="btn-agregar">
            <i class="fas fa-plus"></i> Agregar Imagen al Carrusel
        </button>

        <div class="cards-container">
            <c:forEach var="item" items="${carruseles}">
                <div class="card-carrusel">
                    <img src="${pageContext.request.contextPath}/${item.rutaImagen}" alt="Imagen carrusel">
                    <div class="card-info">
                        <h3>${item.titulo}</h3>
                        <p>${item.descripcion}</p>
                        <span class="estado ${item.estado == 1 ? 'activo' : 'inactivo'}">
                            ${item.estado == 1 ? 'ACTIVO' : 'INACTIVO'}
                        </span>
                        <div class="acciones">
                            <button class="btn-editar"
                                    data-id="${item.id}"
                                    data-titulo="${item.titulo}"
                                    data-descripcion="${item.descripcion}"
                                    data-estado="${item.estado}">
                                <i class="fas fa-edit"></i> Editar
                            </button>
                            <form action="EliminarCarruselServlet" method="post" class="form-eliminar">
                                <input type="hidden" name="id" value="${item.id}" />
                                <button class="btn-eliminar" type="submit">
                                    <i class="fas fa-trash-alt"></i> Eliminar
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<!-- MODAL AGREGAR -->
<div id="modalCarrusel" class="modal-overlay" style="display: none;">
    <div class="modal-content">
        <span class="close-modal" id="btnCerrarModalCarrusel">&times;</span>
        <h2>Agregar Imagen al Carrusel</h2>
        <form action="AgregarCarruselServlet" method="post" enctype="multipart/form-data" class="form-carrusel">
            <label>Imagen:</label>
            <input type="file" name="imagen" required />
            <label>Título:</label>
            <input type="text" name="titulo" maxlength="100" required />
            <label>Descripción:</label>
            <textarea name="descripcion" maxlength="300"></textarea>
            <label>Estado:</label>
            <select name="estado">
                <option value="1">ACTIVO</option>
                <option value="0">INACTIVO</option>
            </select>
            <button type="submit">Guardar</button>
        </form>
    </div>
</div>

<!-- MODAL EDITAR -->
<div id="modalEditarCarrusel" class="modal-overlay" style="display: none;">
    <div class="modal-content">
        <span class="close-modal" id="btnCerrarModalEditar">&times;</span>
        <h2>Editar Imagen del Carrusel</h2>
        <form action="EditarCarruselServlet" method="post" enctype="multipart/form-data" class="form-carrusel">
            <input type="hidden" name="id" id="edit-id" />
            <label>Título:</label>
            <input type="text" name="titulo" id="edit-titulo" maxlength="100" required />
            <label>Descripción:</label>
            <textarea name="descripcion" id="edit-descripcion" maxlength="300"></textarea>
            <label>Estado:</label>
            <select name="estado" id="edit-estado">
                <option value="1">ACTIVO</option>
                <option value="0">INACTIVO</option>
            </select>
            <label>Imagen (opcional):</label>
            <input type="file" name="imagen" />
            <button type="submit">Actualizar</button>
        </form>
    </div>
</div>

<script>
    document.getElementById("btnAbrirModalCarrusel").onclick = () => {
        document.getElementById("modalCarrusel").style.display = "flex";
    };
    document.getElementById("btnCerrarModalCarrusel").onclick = () => {
        document.getElementById("modalCarrusel").style.display = "none";
    };

    document.getElementById("btnCerrarModalEditar").onclick = () => {
        document.getElementById("modalEditarCarrusel").style.display = "none";
    };

    document.querySelectorAll(".btn-editar").forEach(btn => {
        btn.addEventListener("click", () => {
            document.getElementById("edit-id").value = btn.dataset.id;
            document.getElementById("edit-titulo").value = btn.dataset.titulo;
            document.getElementById("edit-descripcion").value = btn.dataset.descripcion;
            document.getElementById("edit-estado").value = btn.dataset.estado;
            document.getElementById("modalEditarCarrusel").style.display = "flex";
        });
    });

    window.onclick = function(event) {
        if (event.target.classList.contains("modal-overlay")) {
            event.target.style.display = "none";
        }
    };
</script>
</body>
</html>
