package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AgregarUsuarioServlet")
public class AgregarUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8"); 
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String rolParam = request.getParameter("idRol");
        int rolId = 0;
        if (rolParam == null || rolParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorRolFaltante");
            return;
        }
        try {
            rolId = Integer.parseInt(rolParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorRolInvalido");
            return;
        }

        int estado = 1; // por defecto activo
        String estadoParam = request.getParameter("estado");
        if (estadoParam != null && !estadoParam.isEmpty()) {
            try {
                estado = Integer.parseInt(estadoParam);
            } catch (NumberFormatException e) {
                estado = 1;
            }
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setDni(dni);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setEstado(estado);
        nuevoUsuario.setIdRol(rolId);

        if (usuarioDAO.existeUsername(username)) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorDuplicadoUsername");
            return;
        }

        if (usuarioDAO.existeDni(dni)) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorDuplicadoDni");
            return;
        }

        boolean guardado = usuarioDAO.guardarUsuario(nuevoUsuario);
        if (guardado) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=exito");
        } else {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=error");
        }
    }
}
