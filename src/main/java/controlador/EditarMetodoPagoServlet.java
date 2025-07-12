package controlador;

import dao.MetodoPagoDAO;
import modelo.MetodoPago;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/editarMetodoPago")
@MultipartConfig
public class EditarMetodoPagoServlet extends HttpServlet {

    private static final String RUTA_RELATIVA = "imagenes/pagos"; 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

         request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("idMetodoPago"));
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        int estado = Integer.parseInt(request.getParameter("estado"));
        String imagenActual = request.getParameter("imagenActual"); 

        // Obtener imagen nueva (si se sube)
        Part filePart = request.getPart("imagen");
        String nombreArchivo = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String rutaImagen;

        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            // Nueva imagen subida
            String rutaAbsoluta = getServletContext().getRealPath("/") + File.separator + RUTA_RELATIVA;
            File directorio = new File(rutaAbsoluta);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            File archivoDestino = new File(directorio, nombreArchivo);
            filePart.write(archivoDestino.getAbsolutePath());

            rutaImagen = RUTA_RELATIVA + "/" + nombreArchivo;
        } else {
            // No se subió nueva imagen, mantener la actual
            rutaImagen = imagenActual;
        }

        MetodoPago metodo = new MetodoPago();
        metodo.setIdMetodoPago(id);
        metodo.setNombre(nombre);
        metodo.setDescripcion(descripcion);
        metodo.setImagen(rutaImagen);
        metodo.setEstado(estado);

        MetodoPagoDAO dao = new MetodoPagoDAO();
        dao.modificarMetodoPago(metodo);

        response.sendRedirect("gestionarMetodoPago");
    }
}
