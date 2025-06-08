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

    private static final String UPLOAD_DIR = "imagenes"; // carpeta relativa en webapp

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Obtener parámetros del formulario
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            int estado = Integer.parseInt(request.getParameter("estado"));
            String imagenActual = request.getParameter("imagenActual"); // nombre de imagen actual

            // Procesar imagen nueva (si hay)
            Part filePart = request.getPart("imagen");
            String fileName = getFileName(filePart);
            String nuevaRutaImagen;

            if (fileName != null && !fileName.isEmpty() && filePart.getSize() > 0) {
                // Nueva imagen seleccionada
                String appPath = request.getServletContext().getRealPath("");
                String uploadPath = appPath + File.separator + UPLOAD_DIR;

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                // Guardar nueva imagen
                filePart.write(uploadPath + File.separator + fileName);
                nuevaRutaImagen = UPLOAD_DIR + "/" + fileName;
            } else {
                // No se seleccionó nueva imagen
                nuevaRutaImagen = imagenActual;
            }

            // Crear objeto producto actualizado
            Producto producto = new Producto();
            producto.setIdProducto(idProducto);
            producto.setIdCategoria(idCategoria);
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);
            producto.setEstado(estado);
            producto.setImagen(nuevaRutaImagen);

            // Actualizar en base de datos
            ProductoDAO productoDAO = new ProductoDAO();
            boolean actualizado = productoDAO.editarProducto(producto);

            if (actualizado) {
                response.sendRedirect("ListarProductosServlet?msg=editado");
            } else {
                response.sendRedirect("ListarProductosServlet?msg=errorEditar");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListarProductosServlet?msg=excepcion");
        }
    }

    // Método auxiliar para obtener nombre del archivo
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
