import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gymDB"; //gymDB is the database
    private static final String USER = "root";
    private static final String PASSWORD = "Deva@2006";

    public static Connection getConnection() {  // user defined function
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);  // this is pre defined function in java.sql
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
