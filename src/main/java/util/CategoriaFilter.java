package util;

import dao.CategoriaDAO;
import modelo.Categoria;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")  
public class CategoriaFilter implements Filter {

    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        List<Categoria> listaCategorias = categoriaDAO.listarCategoriasActivas();
        request.setAttribute("listaCategorias", listaCategorias);

        chain.doFilter(request, response);
    }
}
