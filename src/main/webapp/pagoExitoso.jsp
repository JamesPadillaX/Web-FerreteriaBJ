<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pago Aceptado</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            font-family: 'Segoe UI', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .contenedor {
            background-color: white;
            border: 1px solid #e0e0e0;
            border-radius: 12px;
            padding: 40px 30px;
            text-align: center;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 90%;
            animation: fadeIn 0.4s ease-in-out;
        }

        .checkmark {
            width: 100px;
            height: 100px;
            display: block;
            margin: 0 auto 25px auto;
        }

        .circle {
            stroke-dasharray: 282.6; /* 2πr para r=45 */
            stroke-dashoffset: 282.6;
            stroke: #4caf50;
            stroke-width: 5;
            fill: none;
            animation: drawCircle 0.6s ease-out forwards;
        }

        .check {
            stroke-dasharray: 70;
            stroke-dashoffset: 70;
            stroke: #4caf50;
            stroke-width: 5;
            fill: none;
            animation: drawCheck 0.4s ease-out forwards;
            animation-delay: 0.6s;
        }

        @keyframes drawCircle {
            to {
                stroke-dashoffset: 0;
            }
        }

        @keyframes drawCheck {
            to {
                stroke-dashoffset: 0;
            }
        }

        .titulo {
            font-size: 20px;
            font-weight: bold;
            color: #333;
            margin-bottom: 8px;
        }

        .subtitulo {
            font-size: 15px;
            color: #666;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @media (max-width: 480px) {
            .contenedor {
                padding: 30px 20px;
            }

            .checkmark {
                width: 80px;
                height: 80px;
            }

            .titulo {
                font-size: 18px;
            }

            .subtitulo {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>

<div class="contenedor">
    <!-- SVG animado con círculo completo y check -->
    <svg class="checkmark" viewBox="0 0 100 100">
        <circle class="circle" cx="50" cy="50" r="45"/>
        <polyline class="check" points="30,55 45,70 75,40" />
    </svg>

    <div class="titulo">¡Pago aceptado!</div>
    <div class="subtitulo">Redirigiendo al carrito...</div>
</div>

<script>
    setTimeout(function () {
        window.location.href = 'carrito.jsp';
    }, 3000);
</script>

</body>
</html>
