package controlador;

import dao.MetodoPagoDAO;
import modelo.MetodoPago;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/gestionarMetodoPago")
public class ListarMetodoPagoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MetodoPagoDAO dao = new MetodoPagoDAO();
        List<MetodoPago> lista = dao.listarMetodosPago();

        request.setAttribute("metodos", lista);
        request.getRequestDispatcher("gestionarMetodoPago.jsp").forward(request, response);
    }
}
