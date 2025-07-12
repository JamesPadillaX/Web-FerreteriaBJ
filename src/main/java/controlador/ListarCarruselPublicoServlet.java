package controlador;

import dao.CarruselDAO;
import modelo.Carrusel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/inicio")
public class ListarCarruselPublicoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarruselDAO dao = new CarruselDAO();
        List<Carrusel> listaActivos = dao.listarActivos();

        request.setAttribute("carruseles", listaActivos);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
