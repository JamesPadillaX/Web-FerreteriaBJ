<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Centro de Ayuda</title>
    <link rel="stylesheet" href="WebContent/css/web/ayuda.css"><%-- Asegúrate que la ruta sea correcta según tu estructura --%>
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="contenedor-principal">
        <div class="banner-ayuda">
            <h1>CONTACTANOS</h1>
            <p>¿Tienes dudas o necesitas ayuda? Aquí te orientamos.</p>
        </div>

        <main>
            <div class="ayuda-contenedor">
                <h2>Centro de Ayuda</h2>
                <div class="ayuda-info">
                    <p><strong>📘 ¿Cómo actualizo mis datos?</strong><br>
                        Dirígete a tu perfil y haz clic en el botón "Editar". Luego podrás modificar tus datos personales.</p>

                    <p><strong>🔐 ¿Olvidaste tu contraseña?</strong><br>
                        Haz clic en “¿Olvidaste tu contraseña?” en la pantalla de inicio de sesión y sigue los pasos para restablecerla.</p>

                    <p><strong>📞 Soporte técnico:</strong><br>
                        Puedes contactarnos de lunes a viernes de 9:00 am a 6:00 pm.</p>

                    <p><strong>📧 Correo de soporte:</strong> soporte@invenbj.com</p>
                    <p><strong>📱 Teléfono:</strong> +51 920 426 495</p>
                </div>

                <div class="whatsapp-contacto">
                    <a href="https://wa.me/51920426495?text=Hola,%20necesito%20ayuda%20con%20mi%20compra%20en%20Ferretería%20BJ" target="_blank">
                        <img src="https://cdn-icons-png.flaticon.com/512/733/733585.png" alt="WhatsApp">
                        <span>Escríbenos por WhatsApp</span>
                    </a>
                </div>
            </div>
        </main>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>
