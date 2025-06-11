package controlador;

import service.CategoriaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AgregarCategoriaServlet")
public class AgregarCategoriaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String nombre = request.getParameter("nombre");

        CategoriaService servicio = new CategoriaService();

        if (servicio.categoriaExiste(nombre)) {
            response.sendRedirect("ListarCategoriasServlet?error=existente");
            return;
        }

        boolean guardado = servicio.registrarCategoria(nombre);

        if (guardado) {
            response.sendRedirect("ListarCategoriasServlet?msg=exito");
        } else {
            response.sendRedirect("ListarCategoriasServlet?error=guardar");
        }
    }
}
