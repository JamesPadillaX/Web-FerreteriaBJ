package controlador;

import dao.RolDAO;
import modelo.Rol;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditarRolServlet")
public class EditarRolServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idRol = Integer.parseInt(request.getParameter("idRol"));
            String nombre = request.getParameter("nombre");
            int estado = Integer.parseInt(request.getParameter("estado"));


            Rol rol = new Rol();
            rol.setIdRol(idRol);
            rol.setNombre(nombre);
            rol.setEstado(estado);


            RolDAO rolDAO = new RolDAO();
            boolean modificado = rolDAO.modificarRol(rol);

            if (modificado) {
                // Redirigir a la lista de roles después de modificar
                response.sendRedirect("ListarRolesServlet");
            } else {
                // Redirigir con error
                response.sendRedirect("gestionarRoles.jsp?error=2");
            }
        } catch (NumberFormatException e) {
            // Manejo de error en parámetros
            e.printStackTrace();
            response.sendRedirect("gestionarRoles.jsp?error=3");
        }
    }
}
