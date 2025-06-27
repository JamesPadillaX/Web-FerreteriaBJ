package controlador;

import dao.DomicilioClienteDAO;
import modelo.DomicilioCliente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AgregarDomicilioServlet")
public class AgregarDomicilioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Obtener datos del formulario
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            String calle = request.getParameter("calle");
            String numero = request.getParameter("numero");
            String distrito = request.getParameter("distrito");
            String provincia = request.getParameter("provincia");
            String departamento = request.getParameter("departamento");
            String referencia = request.getParameter("referencia");
            boolean principal = request.getParameter("principal") != null;

            // Crear objeto DomicilioCliente
            DomicilioCliente domicilio = new DomicilioCliente();
            domicilio.setIdCliente(idCliente);
            domicilio.setCalle(calle);
            domicilio.setNumero(numero);
            domicilio.setDistrito(distrito);
            domicilio.setProvincia(provincia);
            domicilio.setDepartamento(departamento);
            domicilio.setReferencia(referencia);
            domicilio.setPrincipal(principal);

            DomicilioClienteDAO dao = new DomicilioClienteDAO();

            // Si se marca como principal, primero desmarcar los anteriores
            if (principal) {
                dao.marcarComoPrincipal(idCliente, -1); // -1 solo para resetear todos
            }

            // Guardar domicilio
            boolean registrado = dao.guardar(domicilio);

            if (registrado) {
                response.sendRedirect("domicilio.jsp?msg=exito");
            } else {
                response.sendRedirect("domicilio.jsp?msg=error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("domicilio.jsp?msg=error");
        }
    }
}
