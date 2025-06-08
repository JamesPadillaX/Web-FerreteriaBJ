package controlador;

import dao.RolDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EliminarRolServlet")
public class EliminarRolServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idRol = Integer.parseInt(request.getParameter("idRol"));

        RolDAO rolDAO = new RolDAO();
        boolean eliminado = rolDAO.eliminarRol(idRol);

        if (eliminado) {
            response.sendRedirect("ListarRolesServlet");
        } else {
            response.sendRedirect("gestionarRoles.jsp?error=1");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
