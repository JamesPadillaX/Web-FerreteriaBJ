package controlador;

import dao.VentaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ActualizarEstadoVentaServlet")
public class ActualizarEstadoVentaServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idVenta = Integer.parseInt(request.getParameter("idVenta"));
        String nuevoEstado = request.getParameter("estado");

        VentaDAO ventaDAO = new VentaDAO();
        boolean actualizado = ventaDAO.actualizarEstadoVenta(idVenta, nuevoEstado);

        if (actualizado) {
            response.sendRedirect("Ventas?mensaje=Estado actualizado correctamente");
        } else {
            response.sendRedirect("Ventas?error=No se pudo actualizar el estado");
        }
    }
}
