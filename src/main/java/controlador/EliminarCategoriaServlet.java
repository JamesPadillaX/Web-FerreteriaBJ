package controlador;

import dao.CategoriaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EliminarCategoriaServlet")
public class EliminarCategoriaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        boolean eliminado = categoriaDAO.eliminarCategoria(idCategoria);

        if (eliminado) {
            response.sendRedirect("ListarCategoriasServlet?msg=eliminado");
        } else {
            response.sendRedirect("gestionarCategorias.jsp?error=1");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
