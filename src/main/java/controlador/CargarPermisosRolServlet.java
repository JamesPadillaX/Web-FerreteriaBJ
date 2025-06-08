package controlador;

import dao.PermisoDAO;
import modelo.Permiso;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/CargarPermisosRolServlet")
public class CargarPermisosRolServlet extends HttpServlet {
    private PermisoDAO permisoDAO;

    @Override
    public void init() throws ServletException {
        permisoDAO = new PermisoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idRolStr = request.getParameter("idRol");
        if (idRolStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta idRol");
            return;
        }

        int idRol = Integer.parseInt(idRolStr);

        // Obtener permisos y nombre del rol
        List<Permiso> todosPermisos = permisoDAO.listarPermisos();
        List<Integer> permisosAsignados = permisoDAO.listarPermisosPorRol(idRol);
        String nombreRol = permisoDAO.obtenerNombreRolPorId(idRol); // NUEVO

        // Enviar datos al JSP
        request.setAttribute("todosPermisos", todosPermisos);
        request.setAttribute("permisosAsignados", permisosAsignados);
        request.setAttribute("idRol", idRol); 
        request.setAttribute("nombreRol", nombreRol); // NUEVO

        request.getRequestDispatcher("WebContent/componentes/modalPermisos.jsp").forward(request, response);
    }
}
