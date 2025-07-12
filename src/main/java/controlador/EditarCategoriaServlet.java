package controlador;

import dao.CategoriaDAO;
import modelo.Categoria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditarCategoriaServlet")
public class EditarCategoriaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setCharacterEncoding("UTF-8"); 
            int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
            String nombre = request.getParameter("nombre");
            int estado = Integer.parseInt(request.getParameter("estado"));
            String nombreFiltro = request.getParameter("nombreFiltro"); // <-- filtro

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(idCategoria);
            categoria.setNombre(nombre);
            categoria.setEstado(estado);

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            boolean modificado = categoriaDAO.modificarCategoria(categoria);

            if (modificado) {
                response.sendRedirect("ListarCategoriasServlet?msg=editado"
                    + (nombreFiltro != null && !nombreFiltro.trim().isEmpty() ? "&nombre=" + nombreFiltro : ""));
            } else {
                response.sendRedirect("ListarCategoriasServlet?msg=categoriaDuplicada"
                    + (nombreFiltro != null && !nombreFiltro.trim().isEmpty() ? "&nombre=" + nombreFiltro : ""));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("gestionarCategorias.jsp?error=2");
        }
    }
}
