package controlador;

import dao.UsuarioDAO;
import dao.VentaDAO;
import dao.ProductoDAO;
import dao.CategoriaDAO;
import modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        VentaDAO ventaDAO = new VentaDAO();

        int totalUsuariosActivos = usuarioDAO.contarUsuariosActivos();
        int totalUsuariosInactivos = usuarioDAO.contarUsuariosInactivos();
        int totalProductosActivos = productoDAO.contarProductosActivos();
        int totalCategoriasActivas = categoriaDAO.contarCategoriasActivas();
        double totalGanancias28Dias = ventaDAO.obtenerTotalVentasCompletadasUltimos28Dias();


        List<Producto> productosBajoStock = productoDAO.listarProductosBajoStock();
        if (productosBajoStock.size() > 4) {
            productosBajoStock = productosBajoStock.subList(0, 4);
        }

        request.setAttribute("totalUsuariosActivos", totalUsuariosActivos);
        request.setAttribute("totalUsuariosInactivos", totalUsuariosInactivos);
        request.setAttribute("totalProductosActivos", totalProductosActivos);
        request.setAttribute("totalCategoriasActivas", totalCategoriasActivas);
        request.setAttribute("productosBajoStock", productosBajoStock);
        request.setAttribute("totalGanancias28Dias", totalGanancias28Dias);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
