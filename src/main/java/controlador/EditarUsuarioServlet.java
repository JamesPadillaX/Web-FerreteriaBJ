package controlador;

import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import service.UsuarioService;

@WebServlet("/EditarUsuarioServlet")
public class EditarUsuarioServlet extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init() {
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int idRol = Integer.parseInt(request.getParameter("idRol"));
        int estado = Integer.parseInt(request.getParameter("estado"));

        // Obtener el usuario original
        Usuario usuarioOriginal = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (usuarioOriginal != null && usuarioOriginal.getIdRol() == 1) {
            if (estado == 0 || idRol != 1) {
                response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorAdmin");
                return;
            }
        }

        // Crear objeto actualizado
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setDni(dni);
        usuario.setTelefono(telefono);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setIdRol(idRol);
        usuario.setEstado(estado);

        // Validar duplicados desde el servicio
        String validacion = usuarioService.validarDuplicados(usuario);
        if ("dni".equals(validacion)) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorDuplicadoDni");
            return;
        }
        if ("username".equals(validacion)) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorDuplicadoUsername");
            return;
        }

        boolean actualizado = usuarioService.actualizarUsuario(usuario);
        if (actualizado) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=editado");
        } else {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorGeneral");
        }
    }
}
