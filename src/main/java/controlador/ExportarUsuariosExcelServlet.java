package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ExportarUsuariosExcelServlet")
public class ExportarUsuariosExcelServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Usuario> usuarios = usuarioDAO.listarUsuarios();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        // Encabezados
        String[] headers = {"ID", "Nombre", "Apellidos", "DNI", "Teléfono", "Usuario", "Rol", "Estado"};
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        int rowNum = 1;
        for (Usuario u : usuarios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(u.getIdUsuario());
            row.createCell(1).setCellValue(u.getNombre());
            row.createCell(2).setCellValue(u.getApellidos());
            row.createCell(3).setCellValue(u.getDni());
            row.createCell(4).setCellValue(u.getTelefono());
            row.createCell(5).setCellValue(u.getUsername());
            row.createCell(6).setCellValue(u.getRol().getNombre());

            String estadoTexto = switch (u.getEstado()) {
                case 1 -> "ACTIVO";
                case 0 -> "INACTIVO";
                case 2 -> "ELIMINADO";
                default -> "Desconocido";
            };
            row.createCell(7).setCellValue(estadoTexto);
        }

        // Ajustar tamaños
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
