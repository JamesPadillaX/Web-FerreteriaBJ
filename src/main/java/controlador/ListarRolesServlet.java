package controlador;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.RolDAO;
import modelo.Rol;

public class ListarRolesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RolDAO rolDAO = new RolDAO();
        List<Rol> listaRoles = rolDAO.listarRoles();

        request.setAttribute("roles", listaRoles);

        request.getRequestDispatcher("gestionarRoles.jsp").forward(request, response);
    }
}
