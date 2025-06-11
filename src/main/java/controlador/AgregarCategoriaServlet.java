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

        try {
            String nombre = request.getParameter("nombre");

            CategoriaService servicio = new CategoriaService();

            if (servicio.categoriaExiste(nombre)) {
                response.sendRedirect("ListarCategoriasServlet?msg=categoriaDuplicada");
                return;
            }

            boolean guardado = servicio.registrarCategoria(nombre);

            if (guardado) {
                response.sendRedirect("ListarCategoriasServlet?msg=exito");
            } else {
                response.sendRedirect("ListarCategoriasServlet?error=guardar");
            }

        } catch (Exception e) {
            response.sendRedirect("ListarCategoriasServlet?error=excepcion");
        }
    }
}
