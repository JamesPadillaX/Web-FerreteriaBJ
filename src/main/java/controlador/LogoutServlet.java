package controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // false: no crear si no existe

        if (session != null) {
            session.invalidate(); // Cierra la sesión actual
        }

        response.sendRedirect("index.jsp"); // Redirige al inicio
    }
}
