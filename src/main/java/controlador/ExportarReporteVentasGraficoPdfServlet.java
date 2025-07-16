package controlador;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import dao.VentaDAO;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Font;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/ExportarReporteVentas")
public class ExportarReporteVentasGraficoPdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String añoParam = request.getParameter("año");
        int año;

        try {
            año = Integer.parseInt(añoParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("ReporteVentas?año=2025&error=parametro_invalido");
            return;
        }

        VentaDAO ventaDAO = new VentaDAO();
        List<int[]> datosVentas = ventaDAO.obtenerVentasPorMes(año);

        if (datosVentas == null || datosVentas.isEmpty()) {
            response.sendRedirect("ReporteVentas?año=" + año + "&error=sin_datos");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_ventas_" + año + ".pdf");

        try (OutputStream out = response.getOutputStream()) {

            Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
            PdfWriter.getInstance(document, out);
            document.open();

            // === Título ===
            com.itextpdf.text.Font fontTitulo = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.HELVETICA, 20, com.itextpdf.text.Font.BOLD, new BaseColor(33, 37, 41));
            Paragraph titulo = new Paragraph("📊 Reporte Anual de Ventas - " + año, fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(10f);
            document.add(titulo);

            // === Subtítulo ===
            com.itextpdf.text.Font fontSubtitulo = new com.itextpdf.text.Font(
                    com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL, BaseColor.DARK_GRAY);
            Paragraph subtitulo = new Paragraph("Resumen visual de las ventas mensuales realizadas en el año", fontSubtitulo);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(15f);
            document.add(subtitulo);

            // === Datos ===
            String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            int[] ventasMes = new int[12];

            for (int[] registro : datosVentas) {
                int mes = registro[0];
                int cantidad = registro[1];
                if (mes >= 1 && mes <= 12) {
                    ventasMes[mes - 1] = cantidad;
                }
            }

            for (int i = 0; i < 12; i++) {
                dataset.addValue(ventasMes[i], "Ventas", meses[i]);
            }

            // === Crear gráfico ===
            JFreeChart chart = ChartFactory.createBarChart(
                    null, "Mes", "Cantidad de Ventas", dataset,
                    PlotOrientation.VERTICAL, false, true, false
            );

            // === Estilo gráfico ===
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setOutlineVisible(false);
            plot.setRangeGridlinePaint(new Color(230, 230, 230));

            // Eje X
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            domainAxis.setLabelFont(new Font("Segoe UI", Font.BOLD, 12));

            // Eje Y
            plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
            plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
            plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            // Barras
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, new Color(78, 115, 223));
            renderer.setBarPainter(new StandardBarPainter());
            renderer.setDrawBarOutline(false);
            renderer.setItemMargin(0.03);

            // Etiquetas sobre las barras
            renderer.setDefaultItemLabelsVisible(true);
            renderer.setDefaultItemLabelFont(new Font("Segoe UI", Font.BOLD, 10));
            renderer.setDefaultItemLabelPaint(Color.DARK_GRAY);

            // === Exportar gráfico como imagen ===
            ByteArrayOutputStream chartOut = new ByteArrayOutputStream();
            ChartUtils.writeChartAsPNG(chartOut, chart, 740, 330); // REDUCIDO ALTO
            Image chartImage = Image.getInstance(chartOut.toByteArray());

            chartImage.setAlignment(Image.ALIGN_CENTER);
            chartImage.scaleToFit(720, 330);
            document.add(chartImage);

            // === Pie de página ===
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Paragraph pie = new Paragraph("Reporte generado el " + sdf.format(new Date()), fontSubtitulo);
            pie.setAlignment(Element.ALIGN_RIGHT);
            pie.setSpacingBefore(10f); // REDUCIDO ESPACIADO
            document.add(pie);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ReporteVentas?año=" + año + "&error=error_pdf");
        }
    }
}
