package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EliminarUsuarioServlet")
public class EliminarUsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

        // Obtener el usuario
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario);

        // Validar si es admin
        if (usuario != null && usuario.getIdRol() == 1) {
            // No permitir eliminar admin
            response.sendRedirect("ListarUsuariosServlet?msg=errorAdmin");
            return;
        }

        // Eliminar (cambiar estado a 2)
        boolean eliminado = usuarioDAO.eliminarUsuario(idUsuario);

        if (eliminado) {
            response.sendRedirect("ListarUsuariosServlet?msg=eliminado");
        } else {
            response.sendRedirect("gestionarUsuarios.jsp?msg=error");
        }
    }
}
