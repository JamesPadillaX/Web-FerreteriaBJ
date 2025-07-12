package controlador;

import modelo.Usuario;
import service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditarUsuarioServlet")
public class EditarUsuarioServlet extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init() {
        usuarioService = new UsuarioService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int idRol = Integer.parseInt(request.getParameter("idRol"));
        int estado = Integer.parseInt(request.getParameter("estado"));

        Usuario usuarioOriginal = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (usuarioOriginal != null && usuarioOriginal.getIdRol() == 1) {
            if (estado == 0 || idRol != 1) {
                response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorAdmin");
                return;
            }
        }

        // Crear objeto con los datos actualizados
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setIdUsuario(idUsuario);
        usuarioActualizado.setNombre(nombre);
        usuarioActualizado.setApellidos(apellidos);
        usuarioActualizado.setDni(dni);
        usuarioActualizado.setTelefono(telefono);
        usuarioActualizado.setUsername(username);
        usuarioActualizado.setPassword(password); // Será hasheada dentro del servicio
        usuarioActualizado.setIdRol(idRol);
        usuarioActualizado.setEstado(estado);

        // Validar duplicados
        String validacion = usuarioService.validarDuplicados(usuarioActualizado);
        if ("dni".equals(validacion)) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorDuplicadoDni");
            return;
        }
        if ("username".equals(validacion)) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorDuplicadoUsername");
            return;
        }

        // Intentar actualizar
        boolean actualizado = usuarioService.actualizarUsuario(usuarioActualizado);
        if (actualizado) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=editado");
        } else {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=errorGeneral");
        }
    }
}
