package controlador;

import dao.CarritoDAO;
import modelo.Cliente;
import modelo.DetalleCarrito;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class CarritoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CarritoDAO carritoDAO = new CarritoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
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

        int idCliente = cliente.getIdCliente();  // Ajusta según el getter

        int idCarrito = carritoDAO.crearCarritoSiNoExiste(idCliente);

        List<DetalleCarrito> detallesCarrito = carritoDAO.listarDetallePorCarrito(idCarrito);

        request.setAttribute("detallesCarrito", detallesCarrito);
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }
}
