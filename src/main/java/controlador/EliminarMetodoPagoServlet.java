package controlador;

import dao.MetodoPagoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/eliminarMetodoPago")
public class EliminarMetodoPagoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("idMetodoPago"));

        MetodoPagoDAO dao = new MetodoPagoDAO();
        dao.eliminarMetodoPago(id);

        response.sendRedirect("gestionarMetodoPago");
    }
}
