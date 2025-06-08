package controlador;

import dao.PermisoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AsignarPermisosServlet")
public class AsignarPermisosServlet extends HttpServlet {
    private PermisoDAO permisoDAO;

    @Override
    public void init() throws ServletException {
        permisoDAO = new PermisoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idRolStr = request.getParameter("idRol");
        String[] permisos = request.getParameterValues("permisos");

        if (idRolStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta idRol");
            return;
        }

        int idRol = Integer.parseInt(idRolStr);

        // Elimina permisos actuales
        permisoDAO.eliminarPermisosDeRol(idRol);

        // Asigna los permisos recibidos
        if (permisos != null) {
            for (String p : permisos) {
                int idPermiso = Integer.parseInt(p);
                permisoDAO.asignarPermisoRol(idRol, idPermiso);
            }
        }

        response.getWriter().write("OK");
    }
}
