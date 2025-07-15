package controlador;

import dao.MetodoPagoDAO;
import modelo.MetodoPago;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/MetodosPago")
public class ListarMetodosPagoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        MetodoPagoDAO dao = new MetodoPagoDAO();
        List<MetodoPago> metodosActivos = dao.listarMetodosPagoActivos();

        request.setAttribute("metodosPago", metodosActivos);
        request.getRequestDispatcher("metodoPago.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }
}
