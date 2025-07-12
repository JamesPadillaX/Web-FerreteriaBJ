package controlador;

import dao.CarruselDAO;
import modelo.Carrusel;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/AgregarCarruselServlet")
@MultipartConfig
public class AgregarCarruselServlet extends HttpServlet {

    private static final String CARPETA_IMAGENES = "imagenes/carrusel/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        CarruselDAO dao = new CarruselDAO();

        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        int estado = Integer.parseInt(request.getParameter("estado"));

        Part filePart = request.getPart("imagen");
        String nombreArchivo = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();

        // Ruta real del servidor para guardar imagen
        String rutaReal = request.getServletContext().getRealPath("/") + CARPETA_IMAGENES;
        File carpeta = new File(rutaReal);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Guardar archivo
        filePart.write(rutaReal + nombreArchivo);

        // Crear objeto
        Carrusel carrusel = new Carrusel();
        carrusel.setTitulo(titulo);
        carrusel.setDescripcion(descripcion);
        carrusel.setEstado(estado);
        carrusel.setRutaImagen(CARPETA_IMAGENES + nombreArchivo);

        boolean exito = dao.registrar(carrusel);
        if (exito) {
            response.sendRedirect("gestionarCarrusel?msg=exito");
        } else {
            response.sendRedirect("gestionarCarrusel?msg=error");
        }
    }
}
