<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>🚫 Acceso Denegado</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            margin: 0;
            padding: 0;
            background: radial-gradient(circle at center, #0a1f2c, #06141b); 
            color: #ffe066;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            overflow: hidden;
            animation: bodyFade 1s ease-in;
        }

        .card {
            background: linear-gradient(145deg, #132f4c, #0e2339);
            padding: 2.5rem 2rem;
            border-radius: 20px;
            text-align: center;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
            width: 90%;
            max-width: 420px;
            backdrop-filter: blur(10px);
            animation: zoomIn 0.8s ease-out forwards;
            opacity: 0;
            transform: scale(0.95);
        }

        .emoji {
            font-size: 4.5rem;
            animation: pulse 1.6s infinite;
            margin-bottom: 1rem;
        }

        h1 {
            font-size: 2.1rem;
            margin-bottom: 0.7rem;
            color: #ffd700;
        }

        p {
            font-size: 1.15rem;
            color: #e0e0e0;
            margin-bottom: 2rem;
        }

        a {
            display: inline-block;
            padding: 0.9rem 1.8rem;
            background: linear-gradient(135deg, #ffd700, #ffc107);
            color: #0a1f2c;
            font-weight: 600;
            text-decoration: none;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(255, 215, 0, 0.3);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        a:hover {
            transform: translateY(-3px) scale(1.02);
            box-shadow: 0 6px 14px rgba(255, 215, 0, 0.5);
            background: linear-gradient(135deg, #ffe066, #ffcc00);
        }

        /* Animaciones */
        @keyframes zoomIn {
            0% {
                opacity: 0;
                transform: scale(0.9) translateY(30px);
                filter: blur(3px);
            }
            100% {
                opacity: 1;
                transform: scale(1) translateY(0);
                filter: blur(0);
            }
        }

        @keyframes pulse {
            0%, 100% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.1);
            }
        }

        @keyframes bodyFade {
            from {
                background-color: #000;
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        @media (max-width: 480px) {
            .card {
                padding: 2rem 1.2rem;
            }

            h1 {
                font-size: 1.6rem;
            }

            p {
                font-size: 1rem;
            }

            .emoji {
                font-size: 3.5rem;
            }

            a {
                padding: 0.8rem 1.5rem;
            }
        }
    </style>
</head>
<body>
    <div class="card">
        <div class="emoji">🚫</div>
        <h1>Acceso Denegado</h1>
        <p>No tienes permisos para entrar a esta sección. 🔒</p>
        <a href="perfilUsuario.jsp">🔐 Volver al Perfil</a>
    </div>
</body>
</html>
