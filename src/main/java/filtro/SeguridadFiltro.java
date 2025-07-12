package filtro;

import modelo.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebFilter("/*")
public class SeguridadFiltro implements Filter {

    private static final Map<String, String> rutaModuloMap = new HashMap<>();

    static {
        rutaModuloMap.put("/dashboard", "Dashboard");
        rutaModuloMap.put("/ListarUsuariosServlet", "Usuarios");
        rutaModuloMap.put("/ListarRolesServlet", "Roles");
        rutaModuloMap.put("/ListarRoles", "Permisos");
        rutaModuloMap.put("/ListarCategoriasServlet", "Categorías");
        rutaModuloMap.put("/ListarProductosServlet", "Productos");
        rutaModuloMap.put("/ventas.jsp", "Ventas");
        rutaModuloMap.put("/reportes.jsp", "Reportes");
        rutaModuloMap.put("/clientes.jsp", "Clientes");
        rutaModuloMap.put("/gestionarCarrusel", "Carrusel");
        // Puedes agregar más rutas protegidas si es necesario
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String path = request.getServletPath();

        // ⚠️ Evitar que un usuario con sesión acceda a loginUsuario.jsp
        if (path.equals("/loginUsuario.jsp")) {
            if (session != null && session.getAttribute("usuario") != null) {
                Usuario usuario = (Usuario) session.getAttribute("usuario");
                if (usuario.getRol() != null && "ADMIN".equalsIgnoreCase(usuario.getRol().getNombre())) {
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/perfilUsuario.jsp");
                }
                return;
            }
        }

        // ✅ Rutas públicas que no deben ser filtradas
        if (path.equals("/login.jsp") || path.equals("/LoginServlet") ||
            path.equals("/index.jsp") || path.equals("/") ||
            path.equals("/recuperarContrasena.jsp") || path.equals("/perfilCliente.jsp") ||
            path.startsWith("/css/") || path.startsWith("/js/") ||
            path.startsWith("/imagenes/") || path.startsWith("/WebContent/")) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ Obtener usuario del sistema en sesión (si existe)
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        @SuppressWarnings("unchecked")
        List<String> modulosPermitidos = (session != null)
                ? (List<String>) session.getAttribute("modulosPermitidos")
                : null;

        // 🔐 Si intenta acceder a rutas protegidas sin estar logueado
        if (rutaModuloMap.containsKey(path)) {
            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/loginUsuario.jsp");
                return;
            }

            // ❌ Si el usuario no tiene permisos o está inactivo
            if (modulosPermitidos == null ||
                !modulosPermitidos.contains(rutaModuloMap.get(path)) ||
                usuario.getEstado() != 1) {
                response.sendRedirect(request.getContextPath() + "/accesoDenegado.jsp");
                return;
            }
        }

        // ✅ Si todo está bien, dejar pasar la solicitud
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No es necesario inicializar nada
    }

    @Override
    public void destroy() {
        // No es necesario destruir nada
    }
}
