package controlador;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.ProductoDAO;
import modelo.Producto;

public class ListarProductosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idCategoriaStr = request.getParameter("idCategoria");
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> listaProductos;

        if (idCategoriaStr != null && !idCategoriaStr.isEmpty()) {
            try {
                int idCategoria = Integer.parseInt(idCategoriaStr);
                listaProductos = productoDAO.listarProductosPorCategoria(idCategoria);
            } catch (NumberFormatException e) {
                listaProductos = productoDAO.listarProductos();
            }
        } else {
            listaProductos = productoDAO.listarProductos();
        }

        request.setAttribute("productos", listaProductos);
        request.getRequestDispatcher("gestionarProductos.jsp").forward(request, response);
    }
}
