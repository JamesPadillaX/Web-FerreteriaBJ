package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ListarUsuariosServlet")
public class ListarUsuariosServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    private void procesarPeticion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        request.setAttribute("usuarios", usuarios);

        String msg = request.getParameter("msg");
        if (msg == null) {
            msg = (String) request.getAttribute("msg"); // <-- aceptar atributo desde forward
        }

        if (msg != null) {
            String mensaje = switch (msg) {
                case "exito" -> "Usuario guardado correctamente.";
                case "errorGeneral" -> "Ocurrió un error al guardar el usuario.";
                case "errorDuplicadoUsername" -> "El nombre de usuario ya existe.";
                case "errorDuplicadoDni" -> "El DNI ya está registrado.";
                case "errorRolFaltante" -> "Debe seleccionar un rol para el usuario.";
                case "errorRolInvalido" -> "El rol seleccionado no es válido.";
                case "editado" -> "Usuario actualizado correctamente.";
                case "errorAdmin" -> "No se puede modificar el estado o rol de un administrador.";
                default -> null;
            };
            request.setAttribute("mensaje", mensaje);
        }

        request.getRequestDispatcher("gestionarUsuarios.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarPeticion(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarPeticion(request, response);
    }
}
