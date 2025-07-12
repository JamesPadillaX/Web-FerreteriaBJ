package controlador;

import dao.ProductoDAO;
import modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/EditarProductoServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 10
)
public class EditarProductoServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "imagenes";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Parámetros del producto
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            int estado = Integer.parseInt(request.getParameter("estado"));
            String imagenActual = request.getParameter("imagenActual");

            // Filtros anteriores (si existen)
            String nombreFiltro = request.getParameter("nombreFiltro");
            String idCategoriaFiltro = request.getParameter("idCategoriaFiltro");

            // Procesar imagen
            Part filePart = request.getPart("imagen");
            String fileName = getFileName(filePart);
            String nuevaRutaImagen;

            if (fileName != null && !fileName.isEmpty() && filePart.getSize() > 0) {
                String appPath = request.getServletContext().getRealPath("");
                String uploadPath = appPath + File.separator + UPLOAD_DIR;

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                filePart.write(uploadPath + File.separator + fileName);
                nuevaRutaImagen = UPLOAD_DIR + "/" + fileName;
            } else {
                nuevaRutaImagen = imagenActual;
            }

            // Actualizar producto
            Producto producto = new Producto();
            producto.setIdProducto(idProducto);
            producto.setIdCategoria(idCategoria);
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);
            producto.setEstado(estado);
            producto.setImagen(nuevaRutaImagen);

            ProductoDAO productoDAO = new ProductoDAO();
            boolean actualizado = productoDAO.editarProducto(producto);

            // Redirigir con los filtros aplicados
            String redirectURL = "ListarProductosServlet?msg=" + (actualizado ? "editado" : "errorEditar");

            if (nombreFiltro != null && !nombreFiltro.isEmpty()) {
                redirectURL += "&nombre=" + nombreFiltro;
            }
            if (idCategoriaFiltro != null && !idCategoriaFiltro.isEmpty()) {
                redirectURL += "&idCategoria=" + idCategoriaFiltro;
            }

            response.sendRedirect(redirectURL);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListarProductosServlet?msg=excepcion");
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return new File(token.substring(token.indexOf('=') + 1).trim().replace("\"", "")).getName();
            }
        }
        return null;
    }
}
