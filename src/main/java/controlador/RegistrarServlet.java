package controlador;

import dao.ClienteDAO;
import modelo.Cliente;
import util.Seguridad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/RegistrarServlet")
public class RegistrarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        clienteDAO = new ClienteDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        // Validar duplicados
        if (clienteDAO.existeDni(dni)) {
            response.sendRedirect("registro.jsp?msg=errorDni");
            return;
        }

        if (clienteDAO.existeCorreo(correo)) {
            response.sendRedirect("registro.jsp?msg=errorCorreo");
            return;
        }

        if (clienteDAO.existeTelefono(telefono)) {
            response.sendRedirect("registro.jsp?msg=errorTelefono");
            return;
        }

        // Hash de la contraseña
        String passwordHash = Seguridad.hashSHA256(password);

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellidos(apellidos);
        cliente.setDni(dni);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setPassword(passwordHash);

        boolean registrado = clienteDAO.registrarCliente(cliente);

        if (registrado) {
            response.sendRedirect("login.jsp?msg=registroExitoso");
        } else {
            response.sendRedirect("registro.jsp?msg=errorRegistro");
        }
    }
}
