package controlador;

import service.RolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AgregarRolServlet")
public class AgregarRolServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AgregarRolServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String nombre = request.getParameter("nombre");

        RolService servicio = new RolService();

        if (servicio.rolExiste(nombre)) {
            logger.warn("Intento de agregar rol duplicado: {}", nombre);
            response.sendRedirect("ListarRolesServlet?msg=rolDuplicado");
            return;
        }

        boolean guardado = servicio.registrarRol(nombre);

        if (guardado) {
            logger.info("Rol registrado correctamente: {}", nombre);
            response.sendRedirect("ListarRolesServlet?msg=exito");
        } else {
            logger.error("Error al guardar rol: {}", nombre);
            response.sendRedirect("ListarRolesServlet?error=guardar");
        }
    }
}
