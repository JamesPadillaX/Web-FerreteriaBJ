package controlador;

import dao.UsuarioDAO;
import dao.VentaDAO;
import dao.ProductoDAO;
import dao.CategoriaDAO;
import dao.ReporteDAO;
import modelo.Producto;
import modelo.ProductoMasVendido;

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
        ReporteDAO reporteDAO = new ReporteDAO(); // 👈 nuevo DAO para reportes

        // Totales del panel
        int totalUsuariosActivos = usuarioDAO.contarUsuariosActivos();
        int totalUsuariosInactivos = usuarioDAO.contarUsuariosInactivos();
        int totalProductosActivos = productoDAO.contarProductosActivos();
        int totalCategoriasActivas = categoriaDAO.contarCategoriasActivas();
        double totalGanancias28Dias = ventaDAO.obtenerTotalVentasCompletadasUltimos28Dias();

        // Productos con bajo stock (limitados a 4)
        List<Producto> productosBajoStock = productoDAO.listarProductosBajoStock();
        if (productosBajoStock.size() > 4) {
            productosBajoStock = productosBajoStock.subList(0, 4);
        }

        // 👇 NUEVO: Obtener los productos más vendidos
        List<ProductoMasVendido> productosMasVendidos = reporteDAO.obtenerProductosMasVendidos();
        if (productosMasVendidos.size() > 10) {
            productosMasVendidos = productosMasVendidos.subList(0, 10); // mostrar solo los top 5
        }

        // Enviar datos al JSP
        request.setAttribute("totalUsuariosActivos", totalUsuariosActivos);
        request.setAttribute("totalUsuariosInactivos", totalUsuariosInactivos);
        request.setAttribute("totalProductosActivos", totalProductosActivos);
        request.setAttribute("totalCategoriasActivas", totalCategoriasActivas);
        request.setAttribute("productosBajoStock", productosBajoStock);
        request.setAttribute("totalGanancias28Dias", totalGanancias28Dias);
        request.setAttribute("productosMasVendidos", productosMasVendidos); // 👈 agregar al request

        // Redirigir al panel principal
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
