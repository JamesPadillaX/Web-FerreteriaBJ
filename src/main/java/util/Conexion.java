package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Conexion instancia;
    private Connection conexion;

    // Datos de conexión
    private final String URL = "jdbc:mysql://localhost:3306/FerreteriaBJ?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "Zercopadilla17";

    // Constructor privado (Singleton)
    private Conexion() {
        conectar();
    }

    // Método que intenta conectar a la base de datos
    private void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: No se encontró el driver JDBC de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos. Verifica que MySQL esté corriendo.");
            e.printStackTrace();
        }
    }

    // Método para obtener la instancia única (Singleton)
    public static Conexion getInstancia() {
        if (instancia == null) {
            synchronized (Conexion.class) {
                if (instancia == null) {
                    instancia = new Conexion();
                }
            }
        }
        return instancia;
    }

    // Método que retorna la conexión (reconecta si está cerrada o es nula)
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                System.out.println("🔄 La conexión estaba cerrada o nula. Intentando reconectar...");
                conectar();
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar el estado de la conexión.");
            e.printStackTrace();
        }

        if (conexion == null) {
            System.err.println("⚠️ Advertencia: la conexión sigue en null. Revisa el servidor MySQL.");
        }

        return conexion;
    }
}
