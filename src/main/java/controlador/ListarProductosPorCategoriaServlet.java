package controlador;

import dao.ProductoDAO;
import modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ListarProductosPorCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String idCategoriaStr = request.getParameter("id");
        String orden = request.getParameter("orden");
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = null;

        try {
            if (idCategoriaStr != null && !idCategoriaStr.isEmpty()) {
                int idCategoria = Integer.parseInt(idCategoriaStr);
                productos = productoDAO.listarProductosPorCategoriaWeb(idCategoria);

                // Ordenar si corresponde
                if (orden != null) {
                    switch (orden) {
                        case "precio_asc":
                            productos.sort((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()));
                            break;
                        case "precio_desc":
                            productos.sort((p1, p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()));
                            break;
                        case "recomendado":
                            // Lógica futura
                            break;
                    }
                }

                request.setAttribute("productos", productos);
                request.setAttribute("idCategoria", idCategoria);
                request.setAttribute("orden", orden);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Categoría inválida");
        }

        request.getRequestDispatcher("productosPorCategoria.jsp").forward(request, response);
    }
}
