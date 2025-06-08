package controlador;

import dao.ProductoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EliminarProductoServlet")
public class EliminarProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        try {
            // Obtener el idProducto desde el parámetro
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));

            // Instanciar DAO y eliminar producto (actualiza estado a 2)
            ProductoDAO productoDAO = new ProductoDAO();
            boolean eliminado = productoDAO.eliminarProducto(idProducto);

            // Redirigir a listado con mensaje según resultado
            if (eliminado) {
                response.sendRedirect("ListarProductosServlet?msg=eliminado");
            } else {
                response.sendRedirect("ListarProductosServlet?msg=error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListarProductosServlet?msg=error");
        }
    }
}
