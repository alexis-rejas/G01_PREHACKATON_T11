package vallegrande.edu.pe.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

/**
 * Singleton - Gestiona conexión a Base de Datos
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final String PROPERTIES_FILE = "/database.properties";

    private String url;
    private String user;
    private String password;

    private DatabaseConnection() {
        loadProperties();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConnection.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new IOException("database.properties no encontrado");
            }
            properties.load(input);
            this.url = properties.getProperty("db.url");
            this.user = properties.getProperty("db.user");
            this.password = properties.getProperty("db.password");
            String driver = properties.getProperty("db.driver");
            Class.forName(driver);
            System.out.println("✓ Propiedades de BD cargadas");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargar propiedades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("✓ Conexión a BD establecida");
            } catch (SQLException e) {
                System.err.println("Error conexión BD: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error cerrar conexión: " + e.getMessage());
        }
    }
}
