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

            RolDAO rolDAO = new RolDAO();

            Rol rolExistente = rolDAO.obtenerRolPorNombre(nombre);

            if (rolExistente != null && rolExistente.getIdRol() != idRol) {
                response.sendRedirect("ListarRolesServlet?msg=rolDuplicado");
                return;
            }

            Rol rol = new Rol();
            rol.setIdRol(idRol);
            rol.setNombre(nombre);
            rol.setEstado(estado);

            boolean modificado = rolDAO.modificarRol(rol);

            if (modificado) {
                response.sendRedirect("ListarRolesServlet?msg=editado");
            } else {
                response.sendRedirect("ListarRolesServlet?error=modificar");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("ListarRolesServlet?error=3");
        }
    }
}
