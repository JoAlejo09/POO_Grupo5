package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 public class DatabaseConnector {
        private static final String HOST="bnpfucoqnut3ygxxnp4h-mysql.services.clever-cloud.com";
        private static final String DB="bnpfucoqnut3ygxxnp4h";
        private static final String USER="ulgbezh1asxgnzvk";
        private static final String PORT="3306";
        private static final String PASSWORD="fN8sFS8o9GHyhvQihONJ";
        private static final String URL = "jdbc:mysql://"+HOST+":"+PORT+"/"+DB; //DIRECCIONAMIENTO DE LA BASE DE DATOS PARA CONEXION EN LA NUBE
        ResultSet rs;

     public static Connection getConnection() throws Exception {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
