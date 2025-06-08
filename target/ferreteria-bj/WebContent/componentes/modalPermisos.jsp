<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-content">
    <span class="close" onclick="cerrarModal()">&times;</span>
    <h2>Permisos: <strong>${nombreRol}</strong> (ID: ${idRol})</h2>

    <form id="formPermisos" method="post" action="AsignarPermisosServlet" onsubmit="return enviarPermisos(event)">
        <input type="hidden" name="idRol" value="${idRol}" />
        <c:forEach var="permiso" items="${todosPermisos}">
            <div class="permiso-item">
                <input type="checkbox" 
                       id="permiso${permiso.idPermiso}" 
                       name="permisos" 
                       value="${permiso.idPermiso}"
                       <c:if test="${permisosAsignados.contains(permiso.idPermiso)}">checked</c:if>
                />
                <label for="permiso${permiso.idPermiso}">${permiso.nombre}</label>
            </div>
        </c:forEach>
        <button type="submit">Guardar Permisos</button>
    </form>
</div>
