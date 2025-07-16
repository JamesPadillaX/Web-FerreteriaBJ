package controlador;

import dao.CarruselDAO;
import dao.ProductoDAO;
import modelo.Carrusel;
import modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/inicio")
public class ListarCarruselPublicoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarruselDAO carruselDAO = new CarruselDAO();
        ProductoDAO productoDAO = new ProductoDAO();


        List<Carrusel> listaCarruseles = carruselDAO.listarActivos();

        List<Producto> productosCategoria1 = productoDAO.listarProductosPorCategoriaWeb(1);
        List<Producto> productosCategoria2 = productoDAO.listarProductosPorCategoriaWeb(2);

        request.setAttribute("carruseles", listaCarruseles);
        request.setAttribute("productosCategoria1", productosCategoria1);
        request.setAttribute("productosCategoria2", productosCategoria2);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
