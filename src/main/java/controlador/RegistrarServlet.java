package controlador;

import modelo.Cliente;
import service.ClienteService;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Cliente cliente = new Cliente();
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setApellidos(request.getParameter("apellidos"));
        cliente.setDni(request.getParameter("dni"));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setCorreo(request.getParameter("correo"));
        String passwordPlano = request.getParameter("password");

        boolean registrado = clienteService.registrarCliente(cliente, passwordPlano);

        if (registrado) {
            response.sendRedirect("login.jsp?msg=registroExitoso");
        } else {
            response.sendRedirect("registro.jsp?msg=errorRegistro");
        }
    }
}
