package controlador;

import dao.UsuarioDAO;
import util.Conexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection con = Conexion.getInstancia().getConexion();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        int totalUsuariosActivos = usuarioDAO.contarUsuariosActivos();
        int totalUsuariosInactivos = usuarioDAO.contarUsuariosInactivos();

        request.setAttribute("totalUsuariosActivos", totalUsuariosActivos);
        request.setAttribute("totalUsuariosInactivos", totalUsuariosInactivos);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
