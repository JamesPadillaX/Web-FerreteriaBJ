package controlador;

import dao.VentaDAO;
import modelo.Cliente;
import modelo.DetalleVenta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/Compras")
public class ProductosCompradosClienteServlet extends HttpServlet {

    private VentaDAO ventaDAO;

    @Override
    public void init() throws ServletException {
        ventaDAO = new VentaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

        if (cliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idCliente = cliente.getIdCliente();

        List<DetalleVenta> listaProductos = ventaDAO.obtenerProductosCompradosPorCliente(idCliente);

        Map<String, List<DetalleVenta>> comprasAgrupadas = new LinkedHashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

        for (DetalleVenta d : listaProductos) {
            Date fechaCompraDate;
            try {
                fechaCompraDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d.getFechaCompra());
            } catch (Exception e) {
                fechaCompraDate = new Date();
            }

            String fechaFormateada = sdf.format(fechaCompraDate);

            comprasAgrupadas.computeIfAbsent(fechaFormateada, k -> new ArrayList<>()).add(d);
        }

        request.setAttribute("comprasAgrupadas", comprasAgrupadas);

        request.getRequestDispatcher("compras.jsp").forward(request, response);
    }
}
