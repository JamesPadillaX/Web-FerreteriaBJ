package util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import java.util.List;


import modelo.DetalleVenta;

public class CorreoUtil {

    public static boolean enviarCodigo(String destino, String codigo) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthentication("eltonpadillayt@gmail.com", "guqfvfcvkatmgmjb"); 
            email.setStartTLSEnabled(true);
            email.setFrom("eltonpadillayt@gmail.com", "Ferretería BJ");
            email.setSubject("🔒 Código de recuperación de contraseña");


            StringBuilder mensajeHtml = new StringBuilder();
            mensajeHtml.append("<html><body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 40px;\">")
                .append("<div style=\"max-width: 500px; margin: auto; background-color: white; padding: 30px; border-radius: 10px; ")
                .append("box-shadow: 0 0 10px rgba(0,0,0,0.1); text-align: center;\">")
                .append("<h1 style=\"color: #0B1D51; margin-bottom: 10px;\">Ferretería <span style=\"color: #FFC107;\">BJ</span></h1>")
                .append("<p style=\"font-size: 18px; color: #333;\">Has solicitado recuperar tu contraseña.</p>")
                .append("<p style=\"font-size: 16px; color: #333;\">Tu código de recuperación es:</p>")
                .append("<div style=\"font-size: 36px; font-weight: bold; color: #FFC107; margin: 20px 0;\">")
                .append(codigo)
                .append("</div>")
                .append("<p style=\"color: #555;\">Ingresa este código en el formulario para continuar con el proceso.</p>")
                .append("<hr style=\"margin: 30px 0;\">")
                .append("<small style=\"color: #888;\">Si no solicitaste esto, puedes ignorar este mensaje.</small>")
                .append("</div></body></html>");

            email.setHtmlMsg(mensajeHtml.toString());
            email.setTextMsg("Tu código de recuperación es: " + codigo); 
            email.addTo(destino);
            email.send();

            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
    }

public static boolean enviarConfirmacionCompra(String destino, String nombreCliente, List<DetalleVenta> productos, double total) {
    try {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthentication("eltonpadillayt@gmail.com", "guqfvfcvkatmgmjb");
        email.setStartTLSEnabled(true);
        email.setFrom("eltonpadillayt@gmail.com", "Ferretería BJ");
        email.setSubject("🛒 ¡Gracias por tu compra en Ferretería BJ!");

        StringBuilder mensajeHtml = new StringBuilder();
        mensajeHtml.append("<html><body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">")
            .append("<div style=\"max-width: 600px; margin: auto; background-color: white; padding: 20px;")
            .append("box-shadow: 0 4px 12px rgba(0,0,0,0.1);\">")
            
            .append("<div style=\"background-color: #ffcc00ff; color: #dededeff; font-size: 22px; font-weight: bold; text-align: center; padding: 20px 10px; margin: 0;\">")
            .append("¡Gracias por tu compra en Ferretería BJ!")
            .append("</div>")


            
            .append("<p style=\"font-size: 18px; text-align: center; color: #333; font-weight: bold; margin-top: 30px;\">")
            .append("¡Hola, ").append(nombreCliente).append("!</p>")
            
            .append("<p style=\"font-size: 15px; text-align: center; color: #555;\">Aquí tienes el resumen de tu compra:</p>")

 
            .append("<table style=\"width: 100%; border-collapse: collapse; margin-top: 20px;\">")
            .append("<thead>")
            .append("<tr>")
            .append("<th style=\"padding: 10px; text-align: left; color: #0B1D51; font-size: 14px; font-weight: bold; border-bottom: 2px solid #FFC107;\">Producto</th>")
            .append("<th style=\"padding: 10px; text-align: right; color: #0B1D51; font-size: 14px; font-weight: bold; border-bottom: 2px solid #FFC107;\">Cantidad</th>")
            .append("</tr>")
            .append("</thead>")
            .append("<tbody>");

        for (DetalleVenta producto : productos) {
            String nombreProducto = producto.getNombreProducto();
            mensajeHtml.append("<tr style=\"border-bottom: 1px solid #eee;\">")
                .append("<td style=\"padding: 12px 8px; font-size: 14px; color: #555;\">")
                .append(nombreProducto)
                .append("</td>")
                .append("<td style=\"padding: 12px 8px; font-size: 14px; color: #333; font-weight: bold; text-align: right;\">")
                .append(producto.getCantidad())
                .append("</td>")
                .append("</tr>");
        }

        mensajeHtml.append("</tbody>")
            .append("</table>")

            .append("<p style=\"font-size: 20px; font-weight: bold; text-align: center; margin-top: 25px; color: #0B1D51;\">Total pagado: <span style='color:#FFC107;'>S/. ")
            .append(String.format("%.2f", total))
            .append("</span></p>")

            .append("<p style=\"text-align: center; font-size: 14px; color: #666; margin-top: 10px;\">")
            .append("Tu pedido está siendo procesado. Te avisaremos cuando sea enviado.")
            .append("</p>")

            .append("<hr style=\"margin: 30px 0;\">")
            .append("<p style=\"text-align: center; font-size: 13px; color: #aaa;\">")
            .append("Gracias por confiar en Ferretería BJ.")
            .append("</p>")

            .append("</div></body></html>");

        email.setHtmlMsg(mensajeHtml.toString());
        email.addTo(destino);
        email.send();

        return true;
    } catch (EmailException e) {
        e.printStackTrace();
        return false;
    }
}



}
