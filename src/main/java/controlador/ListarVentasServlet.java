package controlador;

import dao.VentaDAO;
import modelo.Venta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/Ventas")
public class ListarVentasServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");

        VentaDAO ventaDAO = new VentaDAO();
        List<Venta> listaVentas;

        // Verificamos si ambas fechas están presentes para aplicar el filtro
        if (fechaInicio != null && !fechaInicio.isEmpty() &&
            fechaFin != null && !fechaFin.isEmpty()) {

            listaVentas = ventaDAO.obtenerVentasPorFecha(fechaInicio, fechaFin);
        } else {
            // Si no hay fechas, se listan todas las ventas
            listaVentas = ventaDAO.obtenerTodasLasVentas();
        }

        // Retenemos los valores del filtro en el JSP
        request.setAttribute("fechaInicio", fechaInicio);
        request.setAttribute("fechaFin", fechaFin);
        request.setAttribute("listaVentas", listaVentas);

        request.getRequestDispatcher("gestionarVentas.jsp").forward(request, response);
    }
}
