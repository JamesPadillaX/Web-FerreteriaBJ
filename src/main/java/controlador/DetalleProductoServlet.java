package controlador;

import dao.ProductoDAO;
import dao.ImagenProductoDAO;
import modelo.Producto;
import modelo.ImagenProducto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/DetalleProductoServlet")
public class DetalleProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int idProducto = Integer.parseInt(idParam);

                ProductoDAO dao = new ProductoDAO();
                Producto producto = dao.obtenerProductoPorId(idProducto);

                if (producto != null) {
                    // ✅ Cargar imágenes secundarias
                    ImagenProductoDAO imagenDAO = new ImagenProductoDAO();
                    List<ImagenProducto> imagenesSecundarias = imagenDAO.listarPorProducto(idProducto);
                    producto.setImagenes(imagenesSecundarias);

                    // ✅ Cargar productos relacionados
                    List<Producto> relacionados = dao.listarProductosPorCategoriaWeb(producto.getIdCategoria());

                    request.setAttribute("producto", producto);
                    request.setAttribute("productosRelacionados", relacionados);

                    request.getRequestDispatcher("detalleProducto.jsp").forward(request, response);
                } else {
                    response.sendRedirect("productosPorCategoria.jsp?error=notfound");
                }

            } catch (NumberFormatException e) {
                response.sendRedirect("productosPorCategoria.jsp?error=invalidid");
            }
        } else {
            response.sendRedirect("productosPorCategoria.jsp?error=missingid");
        }
    }
}
