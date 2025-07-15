package controlador;

import dao.MetodoPagoDAO;
import modelo.MetodoPago;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/SeleccionarMetodoPagoServlet")
public class SeleccionarMetodoPagoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idMetodoPago = Integer.parseInt(request.getParameter("idMetodoPago"));
            String metodoEnvio = request.getParameter("metodoEnvio");
            String total = request.getParameter("total");
            String idCarrito = request.getParameter("idCarrito");

            MetodoPagoDAO metodoPagoDAO = new MetodoPagoDAO();
            MetodoPago metodo = metodoPagoDAO.obtenerMetodoPagoPorId(idMetodoPago);

            if (metodo == null) {
                response.sendRedirect("metodoPago.jsp?error=metodoInvalido");
                return;
            }

            String nombreMetodo = metodo.getNombre().toLowerCase().trim();
            String url = "";

            if (nombreMetodo.contains("yape")) {
                url = "pagoYape.jsp";
            } else if (nombreMetodo.contains("plin")) {
                url = "pagoPlin.jsp";
            } else if (nombreMetodo.contains("bcp")) {
                url = "pagoBCP.jsp";
            } else {
                url = "pagoGenerico.jsp"; 
            }


            response.sendRedirect(url + "?idMetodoPago=" + idMetodoPago +
                    "&metodoEnvio=" + metodoEnvio +
                    "&idCarrito=" + idCarrito +
                    "&total=" + total);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pago.jsp?error=exception");
        }
    }
}
