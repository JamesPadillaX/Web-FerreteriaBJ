package controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LogoutUsuarioServlet")
public class LogoutUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute("usuario");             
            session.removeAttribute("modulosPermitidos");   
        }

        response.sendRedirect("loginUsuario.jsp");
    }
}
