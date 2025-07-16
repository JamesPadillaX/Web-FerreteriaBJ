package controlador;

import dao.DomicilioClienteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/eliminarDomicilio")
public class EliminarDomicilioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idDomicilio = Integer.parseInt(request.getParameter("idDomicilio"));

        DomicilioClienteDAO dao = new DomicilioClienteDAO();
        dao.eliminarDomicilio(idDomicilio);

        response.sendRedirect("domicilio.jsp?msg=eliminadoDomicilio");
    }
}
