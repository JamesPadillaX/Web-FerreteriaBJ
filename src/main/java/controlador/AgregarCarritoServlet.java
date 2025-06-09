package controlador;

import dao.CarritoDAO;
import modelo.Cliente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AgregarAlCarritoServlet")
public class AgregarCarritoServlet extends HttpServlet {
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

        int idCliente = cliente.getIdCliente();  // Ajusta según el nombre del getter

        int idCarrito = carritoDAO.crearCarritoSiNoExiste(idCliente);

        String idProductoStr = request.getParameter("idProducto");
        String cantidadStr = request.getParameter("cantidad");

        if (idProductoStr != null && cantidadStr != null) {
            try {
                int idProducto = Integer.parseInt(idProductoStr);
                int cantidad = Integer.parseInt(cantidadStr);

                if (cantidad > 0) {
                    carritoDAO.agregarProductoAlCarrito(idCarrito, idProducto, cantidad);
                }
            } catch (NumberFormatException e) {
                // manejar error si quieres
            }
        }

        String referer = request.getHeader("Referer");
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("ListarProductosPorCategoriaServlet");
        }
    }
}
