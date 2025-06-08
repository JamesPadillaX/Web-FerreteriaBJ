package controlador;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.CategoriaDAO;
import modelo.Categoria;

public class ListarCategoriasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<Categoria> listaCategorias = categoriaDAO.listarCategorias();

        request.setAttribute("categorias", listaCategorias);

        request.getRequestDispatcher("gestionarCategorias.jsp").forward(request, response);
    }
}
