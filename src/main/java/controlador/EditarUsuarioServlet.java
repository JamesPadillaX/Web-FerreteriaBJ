package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EditarUsuarioServlet")
public class EditarUsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
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

        // Obtener el usuario original desde la BD
        Usuario usuarioOriginal = usuarioDAO.obtenerUsuarioPorId(idUsuario);

        // Validar si el usuario es administrador
        if (usuarioOriginal != null && usuarioOriginal.getIdRol() == 1) {
            // No permitir inactivar administradores
            if (estado == 0) {
                response.sendRedirect("ListarUsuariosServlet?msg=errorAdmin");
                return;
            }

            // (Opcional) No permitir cambiar el rol del admin
            if (idRol != 1) {
                response.sendRedirect("ListarUsuariosServlet?msg=errorAdmin");
                return;
            }
        }

        // Crear objeto con datos actualizados
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

        boolean actualizado = usuarioDAO.modificarUsuario(usuario);

        if (actualizado) {
            response.sendRedirect(request.getContextPath() + "/ListarUsuariosServlet?msg=editado");
        } else {
            response.sendRedirect("gestionarUsuarios.jsp?msg=error");
        }
    }
}
