package controlador;

import dao.CarruselDAO;
import modelo.Carrusel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/gestionarCarrusel")
public class ListarCarruselServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarruselDAO dao = new CarruselDAO();
        List<Carrusel> lista = dao.listarTodos();

        request.setAttribute("carruseles", lista);
        request.getRequestDispatcher("gestionarCarrusel.jsp").forward(request, response);
    }
}
