package controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/VerificarCodigoCambioPasswordServlet")
public class VerificarCodigoCambioPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession();
        String codigoReal = (String) sesion.getAttribute("codigoCambioPassword");
        String codigoIngresado = request.getParameter("codigoIngresado");

        if (codigoReal != null && codigoReal.equals(codigoIngresado)) {
            sesion.setAttribute("codigoVerificado", true);
            response.sendRedirect("perfilCliente.jsp?codigoVerificado=true");
        } else {
            response.sendRedirect("perfilCliente.jsp?codigoError=true");
        }
    }
}
