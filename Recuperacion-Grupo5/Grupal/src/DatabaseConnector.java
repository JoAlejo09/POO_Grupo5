import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnector {

    private static final String HOST="bstauj1gxcbpaul5tcg8-mysql.services.clever-cloud.com";
    private static final String DB="bstauj1gxcbpaul5tcg8";
    private static final String USER="urufurcjfvq8w6yh";
    private static final String PORT="3306";
    private static final String PASSWORD="ehwKD1ss0dZXoFaGel2O";
    private static final String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DB; //DIRECCIONAMIENTO DE LA BASE DE DATOS PARA CONEXION EN LA NUBE



    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
