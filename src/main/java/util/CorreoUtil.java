package util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

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
}
