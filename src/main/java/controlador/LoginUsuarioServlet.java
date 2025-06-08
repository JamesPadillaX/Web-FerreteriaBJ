package controlador;

import dao.PermisoDAO;
import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class LoginUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener datos del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.obtenerUsuarioPorUsername(username);

        if (usuario != null && usuario.getPassword().equals(password)) {
            // Imprimir estado para depuración
            System.out.println("Estado usuario: " + usuario.getEstado());

            if (usuario.getEstado() == 1) { // Usuario ACTIVO
                // Crear sesión y guardar usuario
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);

                // Obtener módulos permitidos por rol
                PermisoDAO permisoDAO = new PermisoDAO();
                List<String> modulosPermitidos = permisoDAO.obtenerModulosPermitidosPorRol(usuario.getIdRol());
                session.setAttribute("modulosPermitidos", modulosPermitidos);

                // Redireccionar a perfilUsuario.jsp (ruta absoluta respecto al contexto)
                response.sendRedirect(request.getContextPath() + "/perfilUsuario.jsp");

            } else {
                // Usuario INACTIVO o ELIMINADO, redirigir a página de error
                response.sendRedirect(request.getContextPath() + "/errorInactivo.jsp");
            }
        } else {
            // Usuario o contraseña incorrectos, mostrar mensaje en loginUsuario.jsp
            request.setAttribute("errorLogin", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/loginUsuario.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Reenviar método POST para manejo centralizado
        doPost(request, response);
    }
}
