package controlador;

import modelo.Usuario;
import service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AgregarUsuarioServlet")
public class AgregarUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rolParam = request.getParameter("idRol");

        // Validación de rol
        if (rolParam == null || rolParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorRolFaltante");
            return;
        }

        int rolId;
        try {
            rolId = Integer.parseInt(rolParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorRolInvalido");
            return;
        }

        int estado = 1; // Activo por defecto
        String estadoParam = request.getParameter("estado");
        if (estadoParam != null && !estadoParam.isEmpty()) {
            try {
                estado = Integer.parseInt(estadoParam);
            } catch (NumberFormatException e) {
                estado = 1;
            }
        }

        // Crear nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setDni(dni);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setEstado(estado);
        nuevoUsuario.setIdRol(rolId);

        // Validar duplicados antes de registrar
        if (usuarioService.validarDuplicados(nuevoUsuario).equals("dni")) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorDuplicadoDni");
            return;
        }
        if (usuarioService.validarDuplicados(nuevoUsuario).equals("username")) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorDuplicadoUsername");
            return;
        }

        // Guardar usuario usando el servicio
        boolean guardado = usuarioService.registrarUsuario(nuevoUsuario);
        if (guardado) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=exito");
        } else {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=error");
        }
    }
}
