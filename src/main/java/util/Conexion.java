package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    private static Conexion instancia;
    private Connection conexion;

    private final String URL = "jdbc:mysql://localhost:3306/FerreteriaBJ?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "Zercopadilla17";

    private static final Logger logger = Logger.getLogger(Conexion.class.getName());

    private Conexion() {
        conectar();
    }

    private void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error: No se encontró el driver JDBC de MySQL.", e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al conectar con la base de datos. Verifica que MySQL esté corriendo.", e);
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            synchronized (Conexion.class) {
                if (instancia == null) {
                    logger.info("Creando nueva instancia de conexión...");
                    instancia = new Conexion();
                }
            }
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                logger.warning("La conexión estaba cerrada o nula. Intentando reconectar...");
                conectar();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al verificar el estado de la conexión.", e);
        }

        if (conexion == null) {
            logger.warning("Advertencia: la conexión sigue en null. Revisa el servidor MySQL.");
        }

        return conexion;
    }
}

