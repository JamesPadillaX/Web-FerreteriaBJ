package controlador;

import dao.ClienteDAO;
import modelo.Cliente;
import util.CorreoUtil;
import util.Seguridad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ActualizarPassword")
public class ActualizarPasswordPerfilServlet extends HttpServlet {

    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        clienteDAO = new ClienteDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String correo = (String) sesion.getAttribute("correoCambioPassword");
        Boolean verificado = (Boolean) sesion.getAttribute("codigoVerificado");

        if (correo == null || verificado == null || !verificado) {
            response.sendRedirect("perfilCliente.jsp?error=sinVerificar");
            return;
        }

        String nuevaPassword = request.getParameter("nuevaPassword");
        String nuevaPasswordHash = Seguridad.hashSHA256(nuevaPassword);

        Cliente cliente = clienteDAO.obtenerClientePorCorreo(correo);

        if (cliente != null) {
            cliente.setPassword(nuevaPasswordHash);
            boolean actualizado = clienteDAO.actualizarCliente(cliente);

            if (actualizado) {
                CorreoUtil.enviarConfirmacionCambioPassword(cliente.getCorreo(), cliente.getNombre());

                sesion.removeAttribute("codigoCambioPassword");
                sesion.removeAttribute("codigoVerificado");
                sesion.removeAttribute("correoCambioPassword");

                response.sendRedirect("perfilCliente.jsp?msg=passwordActualizada");
            } else {
                response.sendRedirect("perfilCliente.jsp?error=falloActualizacion");
            }
        } else {
            response.sendRedirect("perfilCliente.jsp?error=usuarioNoEncontrado");
        }
    }
}
