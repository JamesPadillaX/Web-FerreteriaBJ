<%@ page import="java.util.List" %>
<%@ page import="dao.CategoriaDAO" %>
<%@ page import="modelo.Categoria" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    CategoriaDAO dao = new CategoriaDAO();
    List<Categoria> categorias = dao.listarCategoriasActivas();
%>

<ul class="lista-categorias">
    <% for (Categoria cat : categorias) { %>
        <li><a href="Categoria?id=<%= cat.getIdCategoria() %>"><%= cat.getNombre() %></a></li>
    <% } %>
</ul>
