package controlador;

import dao.DomicilioClienteDAO;
import modelo.Cliente;
import modelo.DomicilioCliente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ListarDomiciliosServlet")
public class ListarDomiciliosServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        Cliente cliente = (Cliente) sesion.getAttribute("clienteLogueado");

        if (cliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        DomicilioClienteDAO dao = new DomicilioClienteDAO();
        List<DomicilioCliente> domicilios = dao.listarPorCliente(cliente.getIdCliente());

        request.setAttribute("listaDomicilios", domicilios);
        request.getRequestDispatcher("domicilio.jsp").forward(request, response);
    }
}
