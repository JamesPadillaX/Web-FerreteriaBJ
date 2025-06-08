package controlador;

import dao.ClienteDAO;
import modelo.Cliente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        clienteDAO = new ClienteDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        Cliente cliente = clienteDAO.validarLogin(correo, password);

        if (cliente != null) {
            HttpSession session = request.getSession();
            session.setAttribute("clienteLogueado", cliente);
            response.sendRedirect("index.jsp");
        } else {
            // En lugar de forward, redireccionamos con parámetros para que el JSP lea y muestre el error
            String correoEncoded = URLEncoder.encode(correo, "UTF-8");
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=1&correo=" + correoEncoded);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
