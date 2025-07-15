package controlador;

import dao.CarritoDAO;
import dao.VentaDAO;
import modelo.Cliente;
import modelo.DetalleCarrito;
import modelo.DetalleVenta;
import modelo.Venta;
import util.CorreoUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ConfirmarPagoYapeServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,  
        maxFileSize = 1024 * 1024 * 10,                
        maxRequestSize = 1024 * 1024 * 50)            
public class ConfirmarPagoYapeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

        if (cliente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idCarrito = Integer.parseInt(request.getParameter("idCarrito"));
        double total = Double.parseDouble(request.getParameter("total"));
        String metodoEnvio = request.getParameter("metodoEnvio");
        String codigoOperacion = request.getParameter("codigoOperacion");
        Part filePart = request.getPart("imagenComprobante");

        String uploadPath = getServletContext().getRealPath("") + File.separator + "imagenes" + File.separator + "comprobantes";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String nombreArchivo = filePart.getSubmittedFileName();

        File archivoExistente = new File(uploadPath + File.separator + nombreArchivo);
        if (archivoExistente.exists()) {
            archivoExistente.delete(); 
        }

        String rutaFisicaArchivo = uploadPath + File.separator + nombreArchivo;
        filePart.write(rutaFisicaArchivo);

        String rutaFinal = "imagenes/comprobantes/" + nombreArchivo;

        CarritoDAO carritoDAO = new CarritoDAO();
        List<DetalleCarrito> detallesCarrito = carritoDAO.listarDetallePorCarrito(idCarrito);


        List<DetalleVenta> detallesVenta = new ArrayList<>();
        for (DetalleCarrito dc : detallesCarrito) {
            DetalleVenta dv = new DetalleVenta();
            dv.setIdProducto(dc.getProducto().getIdProducto());
            dv.setCantidad(dc.getCantidad());
            dv.setPrecioUnitario(dc.getProducto().getPrecio());
            dv.setNombreProducto(dc.getProducto().getNombre());  
            detallesVenta.add(dv);
        }

        Venta venta = new Venta();
        venta.setIdCliente(cliente.getIdCliente());
        venta.setIdMetodoPago(1); 
        venta.setMetodoEnvio(metodoEnvio);
        venta.setTotal(total);
        venta.setEstado("PENDIENTE");
        venta.setCodigoOperacion(codigoOperacion);
        venta.setComprobante(rutaFinal); 

        VentaDAO ventaDAO = new VentaDAO();
        int idVenta = ventaDAO.registrarVenta(venta, detallesVenta);
        
        if (idVenta > 0) {
            carritoDAO.vaciarCarrito(idCarrito);
            carritoDAO.finalizarCarrito(idCarrito);

            CorreoUtil.enviarConfirmacionCompra(
        cliente.getCorreo(),
        cliente.getNombre(),
        detallesVenta,
        total
    );

    response.sendRedirect("pagoExitoso.jsp");
}
else {
            request.setAttribute("error", "No se pudo completar la venta.");
            request.getRequestDispatcher("pagoYape.jsp").forward(request, response);
        }
    }
}
