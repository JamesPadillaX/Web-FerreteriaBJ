package controlador;

import dao.RolDAO;
import modelo.Rol;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AgregarRolServlet")
public class AgregarRolServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); 
        String nombre = request.getParameter("nombre");


        Rol rol = new Rol();
        rol.setNombre(nombre);
        rol.setEstado(1); 

        // Guardar rol usando DAO
        RolDAO rolDAO = new RolDAO();
        boolean guardado = rolDAO.guardarRol(rol);

        // Redirigir según resultado
        if (guardado) {
            response.sendRedirect("ListarRolesServlet?msg=exito");
        } else {
            response.sendRedirect("gestionarRoles.jsp?error=1");
        }
    }
}
