package controlador;

import dao.CarritoDAO;
import modelo.Cliente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EliminarDelCarritoServlet")
public class EliminarDelCarritoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CarritoDAO carritoDAO;

    @Override
    public void init() throws ServletException {
        carritoDAO = new CarritoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");
        if (cliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idCliente = cliente.getIdCliente();
        int idCarrito = carritoDAO.obtenerCarritoActivo(idCliente);

        String idProductoStr = request.getParameter("idProducto");

        if (idProductoStr != null) {
            try {
                int idProducto = Integer.parseInt(idProductoStr);
                carritoDAO.eliminarProductoDelCarrito(idCarrito, idProducto);
            } catch (NumberFormatException e) {
                e.printStackTrace(); 
            }
        }

        response.sendRedirect("carrito.jsp?msg=eliminado");
    }
}
