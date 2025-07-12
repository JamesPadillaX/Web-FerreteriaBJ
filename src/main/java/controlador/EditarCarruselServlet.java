package controlador;

import dao.CarruselDAO;
import modelo.Carrusel;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/EditarCarruselServlet")
@MultipartConfig
public class EditarCarruselServlet extends HttpServlet {

    private static final String CARPETA_IMAGENES = "imagenes/carrusel/";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        CarruselDAO dao = new CarruselDAO();
        Carrusel carrusel = new Carrusel();

        int id = Integer.parseInt(request.getParameter("id"));
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        int estado = Integer.parseInt(request.getParameter("estado"));

        Part filePart = request.getPart("imagen");
        String nombreArchivo = filePart.getSubmittedFileName();

        if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
            String nuevoNombre = System.currentTimeMillis() + "_" + nombreArchivo;

            String rutaReal = request.getServletContext().getRealPath("/") + CARPETA_IMAGENES;
            File carpeta = new File(rutaReal);
            if (!carpeta.exists()) carpeta.mkdirs();

            filePart.write(rutaReal + nuevoNombre);
            carrusel.setRutaImagen(CARPETA_IMAGENES + nuevoNombre);
        }

        carrusel.setId(id);
        carrusel.setTitulo(titulo);
        carrusel.setDescripcion(descripcion);
        carrusel.setEstado(estado);

        boolean actualizado = dao.actualizar(carrusel);

        if (actualizado) {
            response.sendRedirect("gestionarCarrusel?msg=editado");
        } else {
            response.sendRedirect("gestionarCarrusel?msg=error");
        }
    }
}
