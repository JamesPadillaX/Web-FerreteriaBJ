package controlador;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        response.setHeader("Content-Disposition", "attachment; filename=reporte_usuarios.pdf");

        try {
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            Document document = new Document(PageSize.A5.rotate(), 36, 36, 54, 36);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Colores
            BaseColor azulElegante = new BaseColor(40, 75, 99);
            BaseColor grisClaro = new BaseColor(245, 245, 245);
            BaseColor doradoSuave = new BaseColor(230, 180, 60);

            // Fuentes (letras rectas)
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, doradoSuave);
            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            // Logo
            String logoPath = getServletContext().getRealPath("/WebContent/images/logo.png");
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(45, 45);

            // Encabezado con logo + títulos
            PdfPTable encabezado = new PdfPTable(2);
            encabezado.setWidthPercentage(100);
            encabezado.setWidths(new float[]{1, 9});

            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setRowspan(2);
            logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell empresaCell = new PdfPCell(new Phrase("Ferretería BJ", tituloFont));
            empresaCell.setBorder(Rectangle.NO_BORDER);
            empresaCell.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell reporteCell = new PdfPCell(new Phrase("REPORTE DE USUARIOS", tituloFont));
            reporteCell.setBorder(Rectangle.NO_BORDER);
            reporteCell.setHorizontalAlignment(Element.ALIGN_LEFT);

            encabezado.addCell(logoCell);
            encabezado.addCell(empresaCell);
            encabezado.addCell(reporteCell);
            encabezado.setSpacingAfter(10f);
            document.add(encabezado);

            // Fecha actual
            String fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            // Usuario logueado
            HttpSession session = request.getSession(false);
            Usuario usuarioLogueado = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
            String nombreUsuario = (usuarioLogueado != null)
                    ? usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellidos()
                    : "Desconocido";

            // Info usuario + fecha
            PdfPTable infoTabla = new PdfPTable(2);
            infoTabla.setWidthPercentage(100);
            infoTabla.setSpacingBefore(5f);
            infoTabla.setWidths(new float[]{6, 4});

            PdfPCell usuarioCell = new PdfPCell(new Phrase("USUARIO: " + nombreUsuario, infoFont));
            usuarioCell.setBorder(Rectangle.NO_BORDER);
            PdfPCell fechaCell = new PdfPCell(new Phrase("FECHA: " + fechaActual, infoFont));
            fechaCell.setBorder(Rectangle.NO_BORDER);
            fechaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            infoTabla.addCell(usuarioCell);
            infoTabla.addCell(fechaCell);
            document.add(infoTabla);

            document.add(new Paragraph(" ")); // Espacio

            // Tabla de usuarios
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 2, 2, 2, 2, 2});
            table.setSpacingBefore(10f);

            // Encabezados
            addStyledHeaderCell(table, "ID", headerFont, azulElegante);
            addStyledHeaderCell(table, "Nombre", headerFont, azulElegante);
            addStyledHeaderCell(table, "Apellidos", headerFont, azulElegante);
            addStyledHeaderCell(table, "DNI", headerFont, azulElegante);
            addStyledHeaderCell(table, "Usuario", headerFont, azulElegante);
            addStyledHeaderCell(table, "Rol", headerFont, azulElegante);
            addStyledHeaderCell(table, "Estado", headerFont, azulElegante);

            // Celdas de datos
            for (Usuario u : usuarios) {
                table.addCell(createBodyCell(String.valueOf(u.getIdUsuario()), bodyFont, BaseColor.WHITE));
                table.addCell(createBodyCell(u.getNombre(), bodyFont, grisClaro));
                table.addCell(createBodyCell(u.getApellidos(), bodyFont, BaseColor.WHITE));
                table.addCell(createBodyCell(u.getDni(), bodyFont, grisClaro));
                table.addCell(createBodyCell(u.getUsername(), bodyFont, BaseColor.WHITE));
                table.addCell(createBodyCell(u.getRol().getNombre(), bodyFont, grisClaro));

                String estado = switch (u.getEstado()) {
                    case 1 -> "Activo";
                    case 0 -> "Inactivo";
                    case 2 -> "Eliminado";
                    default -> "Desconocido";
                };
                table.addCell(createBodyCell(estado, bodyFont, BaseColor.WHITE));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new IOException("Error al generar el PDF", e);
        }
    }

    // Encabezado estilizado
    private void addStyledHeaderCell(PdfPTable table, String content, Font font, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(backgroundColor);
        cell.setPadding(6f);
        table.addCell(cell);
    }

    // Celdas del cuerpo
    private PdfPCell createBodyCell(String content, Font font, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5f);
        return cell;
    }
}
