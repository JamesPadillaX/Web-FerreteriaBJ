package controlador;

import dao.VentaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ReporteVentas")
public class ReporteVentasServlet extends HttpServlet {

    private VentaDAO ventaDAO = new VentaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leer parámetro año desde la URL, ejemplo: ?anio=2025
        String anioParam = request.getParameter("año");
        int anio = 0;

        // Si no envían año, usar el año actual
        if (anioParam != null && !anioParam.isEmpty()) {
            anio = Integer.parseInt(anioParam);
        } else {
            anio = java.time.Year.now().getValue();
        }

        // Obtener datos desde el DAO
        List<int[]> datosVentas = ventaDAO.obtenerVentasPorMes(anio);

        // Enviar datos a la vista
        request.setAttribute("datosVentas", datosVentas);
        request.setAttribute("año", anio);

        // Redireccionar al JSP
        request.getRequestDispatcher("/reporteVentas.jsp").forward(request, response);
    }
}
