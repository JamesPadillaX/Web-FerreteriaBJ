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

        // Invalida la sesión actual
        HttpSession session = request.getSession(false); // No crea nueva si no existe
        if (session != null) {
            session.invalidate(); // Cierra sesión
        }

        // Redirige al login
        response.sendRedirect("loginUsuario.jsp");
    }
}
