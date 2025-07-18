package controlador;

import dao.ClienteDAO;
import modelo.Cliente;
import util.CorreoUtil;
import util.Seguridad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ActualizarContrasenaServlet")
public class ActualizarContrasenaServlet extends HttpServlet {
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        clienteDAO = new ClienteDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nuevaPassword = request.getParameter("nuevaPassword");
        String nuevaPasswordHash = Seguridad.hashSHA256(nuevaPassword);

        HttpSession sesion = request.getSession();
        String correo = (String) sesion.getAttribute("correoRecuperacion");

        if (correo == null) {
            response.sendRedirect("recuperarContrasena.jsp?error=sesionExpirada");
            return;
        }

        Cliente cliente = clienteDAO.obtenerClientePorCorreo(correo);
        if (cliente != null) {
            cliente.setPassword(nuevaPasswordHash);
            boolean actualizado = clienteDAO.actualizarCliente(cliente);

            if (actualizado) {
                CorreoUtil.enviarConfirmacionCambioPassword(cliente.getCorreo(), cliente.getNombre());
                sesion.removeAttribute("codigoRecuperacion");
                sesion.removeAttribute("correoRecuperacion");

                response.sendRedirect("login.jsp?msg=contrasenaActualizada");
            } else {
                response.sendRedirect("nuevaContrasena.jsp?error=falloActualizacion");
            }
        } else {
            response.sendRedirect("nuevaContrasena.jsp?error=usuarioNoEncontrado");
        }
    }
}
