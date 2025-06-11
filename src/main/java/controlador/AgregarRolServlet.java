package controlador;

import service.RolService;

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

        RolService servicio = new RolService();

        if (servicio.rolExiste(nombre)) {
            response.sendRedirect("ListarRolesServlet?msg=rolDuplicado");
            return;
        }

        boolean guardado = servicio.registrarRol(nombre);

        if (guardado) {
            response.sendRedirect("ListarRolesServlet?msg=exito");
        } else {
            response.sendRedirect("ListarRolesServlet?error=guardar");
        }
    }
}
