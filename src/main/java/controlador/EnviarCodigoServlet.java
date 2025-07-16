package controlador;

import dao.ClienteDAO;
import modelo.Cliente;
import util.CorreoUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Random;

@WebServlet("/EnviarCodigoServlet")
public class EnviarCodigoServlet extends HttpServlet {
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        clienteDAO = new ClienteDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        Cliente cliente = clienteDAO.obtenerClientePorCorreo(correo);
        HttpSession sesion = request.getSession();

        if (cliente != null) {
            String codigo = String.valueOf(new Random().nextInt(900000) + 100000);
            sesion.setAttribute("codigoRecuperacion", codigo);
            sesion.setAttribute("correoRecuperacion", correo);

            boolean enviado = CorreoUtil.enviarCodigo(correo, codigo);
            if (enviado) {
                response.sendRedirect("verificarCodigo.jsp");
            } else {
                sesion.setAttribute("mensajeError", "No se pudo enviar el código. Intenta más tarde.");
                response.sendRedirect("recuperarContrasena.jsp");
            }
        } else {
            sesion.setAttribute("mensajeError", "El correo ingresado no está registrado.");
            response.sendRedirect("recuperarContrasena.jsp");
        }
    }
}
