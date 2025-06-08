<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sección en Desarrollo</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #0b1f3a; /* azul marino */
            color: #f9d342; /* amarillo */
            font-family: 'Segoe UI', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
            flex-direction: column;
        }

        h1 {
            font-size: 2.8em;
            margin-bottom: 10px;
        }

        p {
            font-size: 1.2em;
            margin-bottom: 40px;
            color: #fce97d;
        }

        .btn {
            background-color: #f9d342;
            color: #0b1f3a;
            padding: 15px 30px;
            border: none;
            border-radius: 10px;
            font-size: 1em;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #f1c40f;
        }

        .icon {
            font-size: 4em;
            margin-bottom: 20px;
            color: #f9d342;
        }
    </style>
</head>
<body>

    <div class="icon">🚧</div>
    <h1>¡Sección en Construcción!</h1>
    <p>Estamos trabajando para traerte esta funcionalidad muy pronto.</p>

    <a href="perfilUsuario.jsp" class="btn">Regresar al Panel Principal</a>

</body>
</html>
