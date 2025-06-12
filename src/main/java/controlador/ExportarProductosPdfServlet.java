package controlador;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dao.CategoriaDAO;
import dao.ProductoDAO;
import modelo.Categoria;
import modelo.Producto;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/ExportarProductosPdfServlet")
public class ExportarProductosPdfServlet extends HttpServlet {

    private ProductoDAO productoDAO;

    @Override
    public void init() {
        productoDAO = new ProductoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_productos.pdf");

        try {
            String idCategoriaParam = request.getParameter("idCategoria");
            List<Producto> productos;
            String nombreCategoria = "Todas";

            if (idCategoriaParam != null && !idCategoriaParam.isEmpty()) {
                int idCategoria = Integer.parseInt(idCategoriaParam);
                productos = productoDAO.listarProductosPorCategoria(idCategoria);

                CategoriaDAO categoriaDAO = new CategoriaDAO();
                Categoria cat = categoriaDAO.obtenerCategoriaPorId(idCategoria);
                if (cat != null) {
                    nombreCategoria = cat.getNombre();
                }
            } else {
                productos = productoDAO.listarProductos();
            }

            Document document = new Document(PageSize.A4.rotate(), 36, 36, 54, 36);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Colores y fuentes
            BaseColor azul = new BaseColor(40, 75, 99);
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, azul);
            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

            // Logo
            String logoPath = getServletContext().getRealPath("/WebContent/images/logo.png");
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(50, 50);

            // Encabezado
            PdfPTable encabezado = new PdfPTable(2);
            encabezado.setWidths(new float[]{1, 9});
            encabezado.setWidthPercentage(100);
            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setRowspan(2);
            PdfPCell empresaCell = new PdfPCell(new Phrase("Ferretería BJ", tituloFont));
            empresaCell.setBorder(Rectangle.NO_BORDER);
            PdfPCell reporteCell = new PdfPCell(new Phrase("REPORTE DE PRODUCTOS", tituloFont));
            reporteCell.setBorder(Rectangle.NO_BORDER);
            encabezado.addCell(logoCell);
            encabezado.addCell(empresaCell);
            encabezado.addCell(reporteCell);
            document.add(encabezado);

            // Fecha, usuario y categoría
            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            HttpSession session = request.getSession(false);
            Usuario u = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
            String usuario = (u != null) ? u.getNombre() + " " + u.getApellidos() : "Desconocido";

            PdfPTable infoTabla = new PdfPTable(2);
            infoTabla.setWidthPercentage(100);
            infoTabla.setWidths(new float[]{6, 4});
            infoTabla.addCell(celdaInfo("USUARIO: " + usuario + "   |   CATEGORÍA: " + nombreCategoria, infoFont, Element.ALIGN_LEFT));
            infoTabla.addCell(celdaInfo("FECHA: " + fecha, infoFont, Element.ALIGN_RIGHT));
            document.add(infoTabla);

            document.add(new Paragraph(" "));

            // Tabla de productos
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.2f, 2.5f, 2.5f, 2.2f, 1.2f, 1.5f, 2.5f});
            table.setSpacingBefore(10f);

            // Encabezados
            addHeaderCell(table, "Nombre", headerFont, azul);
            addHeaderCell(table, "Descripción", headerFont, azul);
            addHeaderCell(table, "Categoría", headerFont, azul);
            addHeaderCell(table, "Precio", headerFont, azul);
            addHeaderCell(table, "Stock", headerFont, azul);
            addHeaderCell(table, "Estado", headerFont, azul);
            addHeaderCell(table, "Imagen", headerFont, azul);

            for (Producto p : productos) {
                table.addCell(createCell(p.getNombre(), bodyFont));
                table.addCell(createCell(p.getDescripcion(), bodyFont));
                table.addCell(createCell(p.getCategoria(), bodyFont));
                table.addCell(createCell(String.format("S/ %.2f", p.getPrecio()), bodyFont));
                table.addCell(createCell(String.valueOf(p.getCantidad()), bodyFont));
                String estado = switch (p.getEstado()) {
                    case 1 -> "Activo";
                    case 0 -> "Inactivo";
                    default -> "Desconocido";
                };
                table.addCell(createCell(estado, bodyFont));

                // Imagen con tamaño fijo
                try {
                    String imgPath = getServletContext().getRealPath("/" + p.getImagen().trim());
                    Image img = Image.getInstance(imgPath);
                    img.scaleToFit(30, 30); // tamaño pequeño
                    PdfPCell imgCell = new PdfPCell(img, true);
                    imgCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    imgCell.setPadding(3);
                    table.addCell(imgCell);
                } catch (Exception ex) {
                    table.addCell(createCell("Sin imagen", bodyFont));
                }
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new IOException("Error al generar PDF de productos", e);
        }
    }

    private void addHeaderCell(PdfPTable table, String text, Font font, BaseColor bgColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
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

    private PdfPCell celdaInfo(String texto, Font fuente, int alineacion) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, fuente));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alineacion);
        return cell;
    }
}
