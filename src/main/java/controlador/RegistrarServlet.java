package controlador;

import modelo.Cliente;
import service.ClienteService;
import util.CorreoUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/RegistrarServlet")
public class RegistrarServlet extends HttpServlet {
    private ClienteService clienteService;

    @Override
    public void init() throws ServletException {
        clienteService = new ClienteService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Cliente cliente = new Cliente();
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setApellidos(request.getParameter("apellidos"));
        cliente.setDni(request.getParameter("dni"));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setCorreo(request.getParameter("correo"));
        String passwordPlano = request.getParameter("password");

        String resultado = clienteService.registrarCliente(cliente, passwordPlano);

        switch (resultado) {
            case "DNI_DUPLICADO":
                request.setAttribute("msg", "errorDni");
                break;

            case "CORREO_DUPLICADO":
                request.setAttribute("msg", "errorCorreo");
                break;

            case "TELEFONO_DUPLICADO":
                request.setAttribute("msg", "errorTelefono");
                break;

            case "EXITO":
                boolean enviado = CorreoUtil.enviarCorreoBienvenida(cliente.getCorreo(), cliente.getNombre());
                if (enviado) {
                    System.out.println("Correo de bienvenida enviado correctamente a: " + cliente.getCorreo());
                } else {
                    System.err.println("ERROR al enviar correo de bienvenida a: " + cliente.getCorreo());
                }

                response.sendRedirect("registroExitoso.jsp");
                return;  

            default:
                request.setAttribute("msg", "errorRegistro");
                break;
        }
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }
}
