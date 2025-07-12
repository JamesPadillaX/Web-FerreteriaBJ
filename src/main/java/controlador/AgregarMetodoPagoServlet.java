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

@WebServlet("/agregarMetodoPago")
@MultipartConfig
public class AgregarMetodoPagoServlet extends HttpServlet {

    private static final String RUTA_RELATIVA = "imagenes/pagos"; 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        int estado = Integer.parseInt(request.getParameter("estado"));

     
        Part filePart = request.getPart("imagen");
        String nombreArchivo = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();


        String rutaAbsoluta = getServletContext().getRealPath("/") + File.separator + RUTA_RELATIVA;
        File directorio = new File(rutaAbsoluta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

      
        File archivoDestino = new File(directorio, nombreArchivo);
        filePart.write(archivoDestino.getAbsolutePath());

   
        MetodoPago metodo = new MetodoPago();
        metodo.setNombre(nombre);
        metodo.setDescripcion(descripcion);
        metodo.setImagen(RUTA_RELATIVA + "/" + nombreArchivo); 
        metodo.setEstado(estado);

        MetodoPagoDAO dao = new MetodoPagoDAO();
        dao.guardarMetodoPago(metodo);

        response.sendRedirect("gestionarMetodoPago");
    }
}
