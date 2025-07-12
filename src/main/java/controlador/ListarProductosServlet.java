package controlador;

import dao.ProductoDAO;
import dao.ImagenProductoDAO;
import modelo.Producto;
import modelo.ImagenProducto;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ListarProductosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros de filtro
        String idCategoriaStr = request.getParameter("idCategoria");
        String nombreFiltro = request.getParameter("nombre");

        ProductoDAO productoDAO = new ProductoDAO();
        ImagenProductoDAO imagenDAO = new ImagenProductoDAO();

        List<Producto> listaProductos;

        boolean tieneNombre = nombreFiltro != null && !nombreFiltro.trim().isEmpty();
        boolean tieneCategoria = idCategoriaStr != null && !idCategoriaStr.trim().isEmpty();

        // Para mantener seleccionados los filtros en el JSP
        request.setAttribute("nombreBuscado", nombreFiltro);
        request.setAttribute("idCategoria", idCategoriaStr);

        try {
            if (tieneNombre && tieneCategoria) {
                int idCategoria = Integer.parseInt(idCategoriaStr);
                listaProductos = productoDAO.buscarPorNombreYCategoria(nombreFiltro.trim(), idCategoria);
            } else if (tieneNombre) {
                listaProductos = productoDAO.buscarPorNombre(nombreFiltro.trim());
            } else if (tieneCategoria) {
                int idCategoria = Integer.parseInt(idCategoriaStr);
                listaProductos = productoDAO.listarProductosPorCategoria(idCategoria);
            } else {
                listaProductos = productoDAO.listarProductos();
            }
        } catch (NumberFormatException e) {
            // Si la categoría no es válida, mostramos todos
            listaProductos = productoDAO.listarProductos();
        }

        // Cargar imágenes secundarias por producto
        for (Producto producto : listaProductos) {
            List<ImagenProducto> imagenes = imagenDAO.listarPorProducto(producto.getIdProducto());
            producto.setImagenes(imagenes);
        }

        request.setAttribute("productos", listaProductos);
        request.getRequestDispatcher("gestionarProductos.jsp").forward(request, response);
    }
}
