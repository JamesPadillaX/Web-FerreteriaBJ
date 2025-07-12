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

    
        String idCategoriaStr = request.getParameter("idCategoria");
        String nombreFiltro = request.getParameter("nombre");

        nombreFiltro = (nombreFiltro != null) ? nombreFiltro.trim() : "";
        boolean tieneNombre = !nombreFiltro.isEmpty();

        boolean tieneCategoria = idCategoriaStr != null && !idCategoriaStr.trim().isEmpty();


        request.setAttribute("nombreBuscado", nombreFiltro);
        request.setAttribute("idCategoria", idCategoriaStr);

        ProductoDAO productoDAO = new ProductoDAO();
        ImagenProductoDAO imagenDAO = new ImagenProductoDAO();
        List<Producto> listaProductos;

        try {
            if (tieneNombre && tieneCategoria) {
                int idCategoria = Integer.parseInt(idCategoriaStr);
                listaProductos = productoDAO.buscarPorNombreYCategoria(nombreFiltro, idCategoria);
            } else if (tieneNombre) {
                listaProductos = productoDAO.buscarPorNombre(nombreFiltro);
            } else if (tieneCategoria) {
                int idCategoria = Integer.parseInt(idCategoriaStr);
                listaProductos = productoDAO.listarProductosPorCategoria(idCategoria);
            } else {
                listaProductos = productoDAO.listarProductos();
            }
        } catch (NumberFormatException e) {

            listaProductos = productoDAO.listarProductos();
        }


        for (Producto producto : listaProductos) {
            List<ImagenProducto> imagenes = imagenDAO.listarPorProducto(producto.getIdProducto());
            producto.setImagenes(imagenes);
        }


        request.setAttribute("productos", listaProductos);
        request.getRequestDispatcher("gestionarProductos.jsp").forward(request, response);
    }
}
