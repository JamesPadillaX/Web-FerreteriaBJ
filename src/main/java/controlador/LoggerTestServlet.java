package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/logtest")
public class LoggerTestServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoggerTestServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        logger.info("INFO: Servlet /logtest accedido");
        logger.warn("WARN: Posible advertencia simulada");
        logger.error("ERROR: Simulación de error");

        response.setContentType("text/plain");
        response.getWriter().println("¡Log generado! Revisa consola y logs/app.log");
    }
}
