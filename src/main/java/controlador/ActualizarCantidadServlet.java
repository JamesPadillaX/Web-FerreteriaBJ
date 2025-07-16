package controlador;

import dao.CarritoDAO;
import modelo.Cliente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ActualizarCantidadServlet")
public class ActualizarCantidadServlet extends HttpServlet {
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
        String accion = request.getParameter("accion"); 

        if (idProductoStr != null && accion != null) {
            try {
                int idProducto = Integer.parseInt(idProductoStr);

                // Obtener cantidad actual
                int cantidadActual = carritoDAO.obtenerCantidadProducto(idCarrito, idProducto);
                int nuevaCantidad = cantidadActual;

                if ("sumar".equals(accion)) {
                    nuevaCantidad++;
                } else if ("restar".equals(accion) && cantidadActual > 1) {
                    nuevaCantidad--;
                }

                carritoDAO.actualizarCantidadProducto(idCarrito, idProducto, nuevaCantidad);

            } catch (NumberFormatException e) {
            }
        }

        response.sendRedirect("carrito.jsp");
    }
}
