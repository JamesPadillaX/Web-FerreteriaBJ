package controlador;

import dao.ProductoWebDAO;
import modelo.ProductoWeb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/productos")
public class ProductosWebServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProductosWebServlet.class.getName());
    private final ProductoWebDAO productoWebDAO = new ProductoWebDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // === 1. Obtener parámetro de búsqueda ===
            String query = request.getParameter("query");

            // === 2. Obtener los 3 productos más vendidos ===
            List<ProductoWeb> masVendidos = productoWebDAO.obtenerMasVendidos(9);
            request.setAttribute("productosMasVendidos", masVendidos);

            // === 3. Obtener productos según búsqueda o todos ===
            List<ProductoWeb> productos;
            if (query != null && !query.trim().isEmpty()) {
                productos = productoWebDAO.buscarProductosActivosPorNombre(query.trim());
            } else {
                productos = productoWebDAO.obtenerTodos();
            }
            request.setAttribute("productos", productos);

            // Mantener el valor del buscador
            request.setAttribute("query", query);

            // === 4. Redirigir al JSP principal de productos ===
            RequestDispatcher dispatcher = request.getRequestDispatcher("productos.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al cargar los productos para la web", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al cargar los productos.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
