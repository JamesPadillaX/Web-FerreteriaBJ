package controlador;

import dao.DomicilioClienteDAO;
import modelo.Cliente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/MarcarPrincipalServlet")
public class MarcarPrincipalServlet extends HttpServlet {

    private final DomicilioClienteDAO domicilioDAO = new DomicilioClienteDAO();
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

    if (cliente == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String idParam = request.getParameter("idDomicilioPrincipal"); // 👈 nombre del input en el JSP
    if (idParam != null) {
        try {
            int idDomicilio = Integer.parseInt(idParam);
            boolean actualizado = domicilioDAO.marcarComoPrincipal(cliente.getIdCliente(), idDomicilio);

            if (actualizado) {
                session.setAttribute("mensaje", "Domicilio marcado como principal exitosamente.");
            } else {
                session.setAttribute("error", "No se pudo marcar el domicilio como principal.");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID inválido.");
        }
    } else {
        session.setAttribute("error", "ID de domicilio no proporcionado.");
    }

    response.sendRedirect("domicilio.jsp");
}
}