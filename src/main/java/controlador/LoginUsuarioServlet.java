package controlador;

import com.google.common.base.Optional;
import dao.PermisoDAO;
import modelo.Usuario;
import service.UsuarioService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class LoginUsuarioServlet extends HttpServlet {

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<Usuario> optionalUsuario = usuarioService.autenticarUsuario(username, password);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            if (usuario.getEstado() == 1) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                // Obtener módulos por rol
                PermisoDAO permisoDAO = new PermisoDAO();
                List<String> modulosPermitidos = permisoDAO.obtenerModulosPermitidosPorRol(usuario.getIdRol());
                session.setAttribute("modulosPermitidos", modulosPermitidos);

                response.sendRedirect(request.getContextPath() + "/perfilUsuario.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/errorInactivo.jsp");
            }

        } else {
            request.setAttribute("errorLogin", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/loginUsuario.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
