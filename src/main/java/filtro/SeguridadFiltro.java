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
        // Más rutas protegidas si es necesario
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String path = request.getServletPath(); 
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        @SuppressWarnings("unchecked")
        List<String> modulosPermitidos = (session != null) ? 
                (List<String>) session.getAttribute("modulosPermitidos") : null;

        // ⚠️ Si ya inició sesión y accede a login, redirigirlo
        if (usuario != null && (path.equals("/loginUsuario.jsp") || path.equals("/login.jsp"))) {
            if (usuario.getRol() != null && "ADMIN".equalsIgnoreCase(usuario.getRol().getNombre())) {
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/perfilUsuario.jsp");
            }
            return;
        }

        // 🔐 Si intenta acceder a rutas protegidas sin login
        if (rutaModuloMap.containsKey(path)) {
            if (usuario == null) {
                response.sendRedirect(request.getContextPath() + "/loginUsuario.jsp");
                return;
            }

            // Si no tiene permisos o está inactivo
            if (modulosPermitidos == null || 
                !modulosPermitidos.contains(rutaModuloMap.get(path)) || 
                usuario.getEstado() != 1) {
                response.sendRedirect(request.getContextPath() + "/accesoDenegado.jsp");
                return;
            }
        }

        // ✅ Si todo está correcto, continuar
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }
}
