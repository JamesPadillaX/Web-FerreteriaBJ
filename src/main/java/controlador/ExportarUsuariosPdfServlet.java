package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ExportarUsuariosPdfServlet")
public class ExportarUsuariosPdfServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.pdf");

        try {
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            // Título
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("Listado de Usuarios", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(Chunk.NEWLINE);

            // Tabla
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 2, 2, 2, 2});

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            addCell(table, "ID", headerFont);
            addCell(table, "Nombre", headerFont);
            addCell(table, "Apellidos", headerFont);
            addCell(table, "DNI", headerFont);
            addCell(table, "Rol", headerFont);
            addCell(table, "Estado", headerFont);

            for (Usuario u : usuarios) {
                table.addCell(String.valueOf(u.getIdUsuario()));
                table.addCell(u.getNombre());
                table.addCell(u.getApellidos());
                table.addCell(u.getDni());
                table.addCell(u.getRol().getNombre());
                table.addCell(u.getEstado() == 1 ? "Activo" : "Inactivo");
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            throw new IOException("Error al generar el PDF", e);
        }
    }

    private void addCell(PdfPTable table, String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(230, 230, 250)); 
        table.addCell(cell);
    }
}
