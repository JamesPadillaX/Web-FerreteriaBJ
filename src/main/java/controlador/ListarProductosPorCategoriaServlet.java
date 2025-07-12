package controlador;

import dao.CategoriaDAO;
import dao.ProductoDAO;
import modelo.Categoria;
import modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
@WebServlet("/Categoria")
public class ListarProductosPorCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String idCategoriaStr = request.getParameter("id");
        String orden = request.getParameter("orden");
        ProductoDAO productoDAO = new ProductoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        try {
            if (idCategoriaStr != null && !idCategoriaStr.trim().isEmpty()) {
                int idCategoria = Integer.parseInt(idCategoriaStr);

                List<Producto> productos = productoDAO.listarProductosPorCategoriaWeb(idCategoria);

                Categoria categoria = categoriaDAO.obtenerCategoriaPorId(idCategoria);
                String nombreCategoria = (categoria != null) ? categoria.getNombre() : "Categoría";

                if (orden != null) {
                    switch (orden) {
                        case "precio_asc":
                            productos.sort((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()));
                            break;
                        case "precio_desc":
                            productos.sort((p1, p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()));
                            break;
                        case "recomendado":
                            break;
                    }
                }

                request.setAttribute("productos", productos);
                request.setAttribute("idCategoria", idCategoria);
                request.setAttribute("orden", orden);
                request.setAttribute("nombreCategoria", nombreCategoria);

            } else {
                request.setAttribute("error", "Categoría no válida.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Error: El ID de categoría no es válido.");
        }

        request.getRequestDispatcher("productosPorCategoria.jsp").forward(request, response);
    }
}
