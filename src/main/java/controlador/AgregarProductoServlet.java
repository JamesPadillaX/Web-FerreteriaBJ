package controlador;

import dao.ProductoDAO;
import modelo.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AgregarProductoServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,  // 1MB antes de escribir en disco
    maxFileSize = 1024 * 1024 * 5,    // max 5MB por archivo
    maxRequestSize = 1024 * 1024 * 10 // max 10MB por solicitud
)
public class AgregarProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIR = "imagenes";  // Carpeta para guardar imágenes

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // Para caracteres especiales

        try {
            // Obtener datos del formulario
            int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            int estado = Integer.parseInt(request.getParameter("estado"));  // Normalmente 1 para activo

            // Obtener la imagen del formulario (campo input type="file" con name="imagen")
            Part filePart = request.getPart("imagen");
            String fileName = getFileName(filePart);

            // Obtener ruta absoluta del proyecto
            String appPath = request.getServletContext().getRealPath("");
            String uploadPath = appPath + java.io.File.separator + UPLOAD_DIR;

            // Crear carpeta imagenes si no existe
            java.io.File uploadDir = new java.io.File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Guardar archivo en la carpeta imagenes
            filePart.write(uploadPath + java.io.File.separator + fileName);

            // Crear objeto Producto y asignar datos
            Producto producto = new Producto();
            producto.setIdCategoria(idCategoria);
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);
            producto.setEstado(estado);
            producto.setImagen(UPLOAD_DIR + "/" + fileName); // Guardar ruta relativa para mostrar luego

            // Guardar producto en base de datos
            ProductoDAO productoDAO = new ProductoDAO();
            boolean agregado = productoDAO.agregarProducto(producto);

            // Redirigir con mensaje según resultado
            if (agregado) {
                response.sendRedirect("ListarProductosServlet?msg=exito");
            } else {
                response.sendRedirect("ListarProductosServlet?msg=error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListarProductosServlet?msg=error");
        }
    }

    // Método auxiliar para obtener el nombre del archivo del Part
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                String fileName = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
                // En algunos navegadores el filename puede venir con ruta completa, solo nombre
                return new java.io.File(fileName).getName();
            }
        }
        return "";
    }
}
