<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>Gestionar Métodos de Pago</title>
    <link rel="stylesheet" href="WebContent/css/panel/panelPrincipal.css">
    <link rel="stylesheet" href="WebContent/css/panel/gestionarPagos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>
    <div class="container">
        <jsp:include page="WebContent/componentes/sidebar.jsp" />
        <div class="content">
            <div class="top-bar">
                <h1>Métodos de Pago</h1>
                <button id="btnAbrirModalAgregar" class="btn-agregar">
                    <i class="fas fa-plus"></i> Agregar Nuevo
                </button>
            </div>

            <div class="card-container">
                <c:forEach var="metodo" items="${metodos}">
                    <div class="card-metodo">
                        <img src="${metodo.imagen}" alt="Método de Pago" class="imagen-pago">
                        <div class="info">
                            <h3>${metodo.nombre}</h3>
                            <p>${metodo.descripcion}</p>
                            <span class="estado ${metodo.estado == 1 ? 'activo' : 'inactivo'}">
                                ${metodo.estado == 1 ? 'ACTIVO' : 'INACTIVO'}
                            </span>
                            <div class="acciones">
                                <button class="btn-editar" 
                                        onclick="abrirModalEditar('${metodo.idMetodoPago}', '${metodo.nombre}', '${metodo.descripcion}', '${metodo.imagen}', '${metodo.estado}')">
                                    <i class="fas fa-pen-to-square"></i>
                                </button>
                                <form action="eliminarMetodoPago" method="post" class="form-eliminar">
                                    <input type="hidden" name="idMetodoPago" value="${metodo.idMetodoPago}" />
                                    <button type="submit" class="btn-eliminar">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>


    <div id="modalAgregar" class="modal">
        <div class="modal-content">
            <h2>Agregar Método de Pago</h2>
            <form action="agregarMetodoPago" method="post" enctype="multipart/form-data">
                <input type="text" name="nombre" placeholder="Nombre" required />
                <textarea name="descripcion" placeholder="Descripción" rows="3"></textarea>
                <input type="file" name="imagen" accept="image/*" required />
                <select name="estado" required>
                    <option value="1">ACTIVO</option>
                    <option value="0">INACTIVO</option>
                </select>
                <div class="modal-buttons">
                    <button type="submit" class="btn-confirmar">Guardar</button>
                    <button type="button" class="btn-cancelar" onclick="cerrarModalAgregar()">Cancelar</button>
                </div>
            </form>
        </div>
    </div>


    <div id="modalEditar" class="modal">
        <div class="modal-content">
            <h2>Editar Método de Pago</h2>
            <form action="editarMetodoPago" method="post" enctype="multipart/form-data">
                <input type="hidden" name="idMetodoPago" id="editarIdMetodoPago" />
                <input type="hidden" name="imagenActual" id="editarImagenActual" />
                <input type="text" name="nombre" id="editarNombre" required />
                <textarea name="descripcion" id="editarDescripcion" rows="3"></textarea>
                <input type="file" name="imagen" accept="image/*" />
                <select name="estado" id="editarEstado" required>
                    <option value="1">ACTIVO</option>
                    <option value="0">INACTIVO</option>
                </select>
                <div class="modal-buttons">
                    <button type="submit" class="btn-confirmar">Guardar Cambios</button>
                    <button type="button" class="btn-cancelar" onclick="cerrarModalEditar()">Cancelar</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function abrirModalEditar(id, nombre, descripcion, imagen, estado) {
            document.getElementById('editarIdMetodoPago').value = id;
            document.getElementById('editarNombre').value = nombre;
            document.getElementById('editarDescripcion').value = descripcion;
            document.getElementById('editarImagenActual').value = imagen;
            document.getElementById('editarEstado').value = estado;
            document.getElementById('modalEditar').style.display = 'flex';
        }

        function cerrarModalEditar() {
            document.getElementById('modalEditar').style.display = 'none';
        }

        document.getElementById("btnAbrirModalAgregar").addEventListener("click", () => {
            document.getElementById("modalAgregar").style.display = "flex";
        });

        function cerrarModalAgregar() {
            document.getElementById("modalAgregar").style.display = "none";
        }

        window.onclick = function(event) {
            if (event.target.classList.contains('modal')) {
                event.target.style.display = "none";
            }
        }
    </script>

    
</body>
</html>
