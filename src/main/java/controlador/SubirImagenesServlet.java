package controlador;

import dao.ImagenProductoDAO;
import modelo.ImagenProducto;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

@WebServlet("/SubirImagenesServlet")
@MultipartConfig
public class SubirImagenesServlet extends HttpServlet {

    private static final String CARPETA_IMAGENES = "imagenes";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener id del producto y categoría
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        String idCategoria = request.getParameter("idCategoria"); // puede ser null o vacío

        // Ruta física del servidor para guardar imágenes
        String pathReal = request.getServletContext().getRealPath("") + File.separator + CARPETA_IMAGENES;
        File carpeta = new File(pathReal);
        if (!carpeta.exists()) {
            carpeta.mkdirs(); // Crear carpeta si no existe
        }

        ImagenProductoDAO dao = new ImagenProductoDAO();

        // Subir imágenes
        try {
            Collection<Part> partes = request.getParts();
            for (Part parte : partes) {
                if (parte.getName().equals("imagenes") && parte.getSize() > 0) {
                    String nombreOriginal = new File(parte.getSubmittedFileName()).getName();
                    String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
                    String nuevoNombre = UUID.randomUUID().toString() + extension;

                    String rutaRelativa = CARPETA_IMAGENES + "/" + nuevoNombre;
                    String rutaAbsoluta = pathReal + File.separator + nuevoNombre;

                    parte.write(rutaAbsoluta);

                    ImagenProducto img = new ImagenProducto();
                    img.setIdProducto(idProducto);
                    img.setRutaImagen(rutaRelativa);
                    dao.agregar(img);
                }
            }

            // Redireccionar según el filtro
            if (idCategoria != null && !idCategoria.isEmpty()) {
                response.sendRedirect("ListarProductosServlet?msg=exito&idCategoria=" + idCategoria);
            } else {
                response.sendRedirect("ListarProductosServlet?msg=exito");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ListarProductosServlet?msg=error");
        }
    }
}
