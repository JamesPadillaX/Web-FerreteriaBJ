package controlador;

import dao.CategoriaDAO;
import modelo.Categoria;

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

        Categoria categoria = new Categoria();
        categoria.setNombre(nombre);
        categoria.setEstado(1); 

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        boolean guardado = categoriaDAO.guardarCategoria(categoria);

        if (guardado) {
            response.sendRedirect("ListarCategoriasServlet?msg=exito");
        } else {
            response.sendRedirect("gestionarCategorias.jsp?error=1");
        }
    }
}
