package controlador;

import util.CorreoUtil;
import util.Seguridad;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EnviarCodigoCambioPasswordServlet")
public class EnviarCodigoCambioPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String codigoGenerado = Seguridad.generarCodigo();  

        HttpSession sesion = request.getSession();
        sesion.setAttribute("codigoCambioPassword", codigoGenerado);
        sesion.setAttribute("correoCambioPassword", correo);
        CorreoUtil.enviarCodigoCambioPassword(correo, codigoGenerado);
        response.sendRedirect("perfilCliente.jsp?codigoEnviado=true");
    }
}
