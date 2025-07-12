package controlador;

import dao.CarruselDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EliminarCarruselServlet")
public class EliminarCarruselServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);
            CarruselDAO dao = new CarruselDAO();
            boolean eliminado = dao.eliminar(id);

            if (eliminado) {
                response.sendRedirect("gestionarCarrusel?msg=eliminado");
            } else {
                response.sendRedirect("gestionarCarrusel?msg=error");
            }
        } else {
            response.sendRedirect("gestionarCarrusel?msg=error");
        }
    }
}
