package controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/VerificarCodigoServlet")
public class VerificarCodigoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoIngresado = request.getParameter("codigo");

        HttpSession sesion = request.getSession();
        String codigoGuardado = (String) sesion.getAttribute("codigoRecuperacion");

        if (codigoGuardado != null && codigoGuardado.equals(codigoIngresado)) {
            response.sendRedirect("nuevaContrasena.jsp");
        } else {
            response.sendRedirect("verificarCodigo.jsp?error=incorrecto");
        }
    }
}
