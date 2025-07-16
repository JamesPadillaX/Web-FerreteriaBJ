package controlador;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dao.VentaDAO;
import modelo.Usuario;
import modelo.Venta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/ExportarVentasPdfServlet")
public class ExportarVentasPdfServlet extends HttpServlet {

    private VentaDAO ventaDAO;

    @Override
    public void init() {
        ventaDAO = new VentaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_ventas.pdf");

        try {
            String fechaInicio = request.getParameter("fechaInicio");
            String fechaFin = request.getParameter("fechaFin");

            List<Venta> ventas;

            if (fechaInicio != null && !fechaInicio.isEmpty() &&
                fechaFin != null && !fechaFin.isEmpty()) {
                ventas = ventaDAO.obtenerVentasPorFecha(fechaInicio, fechaFin);
            } else {
                ventas = ventaDAO.obtenerTodasLasVentas();
            }

            Document document = new Document(PageSize.A4.rotate(), 36, 36, 54, 36);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            BaseColor azul = new BaseColor(40, 75, 99);
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, azul);
            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            Paragraph titulo = new Paragraph("REPORTE DE VENTAS", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph(" "));

            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            HttpSession session = request.getSession(false);
            Usuario u = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
            String usuario = (u != null) ? u.getNombre() + " " + u.getApellidos() : "Desconocido";

            Paragraph info = new Paragraph("Generado por: " + usuario + "    |    Fecha: " + fecha, infoFont);
            info.setAlignment(Element.ALIGN_RIGHT);
            document.add(info);

            document.add(new Paragraph(" "));

            String mensajeFiltro = (fechaInicio != null && fechaFin != null)
                    ? "Filtro aplicado: desde " + fechaInicio + " hasta " + fechaFin
                    : "Reporte sin filtro de fechas";

            Paragraph filtroParrafo = new Paragraph(mensajeFiltro, infoFont);
            filtroParrafo.setAlignment(Element.ALIGN_LEFT);
            document.add(filtroParrafo);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3f, 2.5f, 2f, 2.5f, 2f});
            table.setSpacingBefore(10f);

            addHeaderCell(table, "Cliente", headerFont, azul);
            addHeaderCell(table, "Pago", headerFont, azul);
            addHeaderCell(table, "Total (S/.)", headerFont, azul);
            addHeaderCell(table, "Fecha", headerFont, azul);
            addHeaderCell(table, "Estado", headerFont, azul);

            for (Venta v : ventas) {
                table.addCell(createCell(v.getNombre(), bodyFont));
                table.addCell(createCell(v.getNombreMetodoPago(), bodyFont));
                table.addCell(createCell(String.format("%.2f", v.getTotal()), bodyFont));
                table.addCell(createCell(v.getFecha(), bodyFont));
                table.addCell(createCell(v.getEstado(), bodyFont));
            }

            document.add(table);

            document.add(new Paragraph(" "));

            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Paragraph totalVentasParrafo = new Paragraph("Total de Ventas Realizadas: " + ventas.size(), totalFont);
            totalVentasParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalVentasParrafo);

            document.close();

        } catch (Exception e) {
            throw new IOException("Error al generar PDF de ventas", e);
        }
    }

    private void addHeaderCell(PdfPTable table, String text, Font font, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6f);
        table.addCell(cell);
    }

    private PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5f);
        return cell;
    }
}
