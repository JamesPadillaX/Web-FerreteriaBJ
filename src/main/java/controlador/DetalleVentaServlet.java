package controlador;

import dao.VentaDAO;
import modelo.DetalleVenta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/DetalleVentaServlet")
public class DetalleVentaServlet extends HttpServlet {
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String idVentaStr = request.getParameter("idVenta");

    if (idVentaStr != null && !idVentaStr.isEmpty()) {
        try {
            int idVenta = Integer.parseInt(idVentaStr);

            VentaDAO ventaDAO = new VentaDAO();
            List<DetalleVenta> detallesVenta = ventaDAO.obtenerDetalleVenta(idVenta);

            request.setAttribute("detallesVenta", detallesVenta);
            request.setAttribute("idVenta", idVenta);

            request.getRequestDispatcher("detalleVenta.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro idVenta no es un número válido.");
        }
    } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro idVenta.");
    }
}

}
