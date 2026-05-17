package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Configuración de la base de datos
	private static final String HOST     = "127.0.0.1";
	private static final String PORT     = "3307";
	private static final String DATABASE = "civilizations";
	private static final String USER	 = "sacerdote";
	private static final String PASSWORD = "C1v1l1z4t10n";
		
	private static final String URL = String.format(
	          "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
	      HOST, PORT, DATABASE
	  );
    // Instancia única (Singleton) 
    private static Connection instancia;

    // Constructor privado para evitar instanciación externa
    private DBConnection() {}

    public static Connection getInstance() {
        try {
            // Si la conexión no existe o se cerró, la creamos
            if (instancia == null || instancia.isClosed()) {
                // Registrar el driver (opcional en versiones modernas, pero buena práctica)
                Class.forName("com.mysql.cj.jdbc.Driver");
                instancia = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a Civilizations DB.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return instancia;
    }

    public static void cerrarConexion() {
        try {
            if (instancia != null && !instancia.isClosed()) {
                instancia.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar: " + e.getMessage());
        }
    }
}