package controlador;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.CategoriaDAO;
import modelo.Categoria;

public class ListarCategoriasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreFiltro = request.getParameter("nombre");
        List<Categoria> listaCategorias;

        if (nombreFiltro != null && !nombreFiltro.trim().isEmpty()) {
            listaCategorias = categoriaDAO.buscarPorNombre(nombreFiltro.trim());
            request.setAttribute("nombreBuscado", nombreFiltro); 
        } else {
            listaCategorias = categoriaDAO.listarCategorias();
        }

        request.setAttribute("categorias", listaCategorias);
        request.getRequestDispatcher("gestionarCategorias.jsp").forward(request, response);
    }
}
