
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class test {

    static Connection conn;
    static final String DB_URL = "jdbc:mysql://sololevelingrpg-samchristopherbinos-e082.j.aivencloud.com:13281/";
    static final String DB_USER = "avnadmin";
    static final String DB_PASSWORD = "AVNS_fgO5G8ZIU1MhjtNNpnq";

    public static void main(String[] args) {
        initialize();
        executeMySQL("DROP table users");
    }

    public static void initialize() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            executeMySQL("CREATE DATABASE IF NOT EXISTS sololevelingrpg");
            executeMySQL("USE sololevelingrpg");
            executeMySQL("CREATE TABLE IF NOT EXISTS users ("
                       + "id INT AUTO_INCREMENT PRIMARY KEY, "
                       + "email VARCHAR(255) UNIQUE NOT NULL, "
                       + "hashed_password VARCHAR(255) NOT NULL"
                       + ")");

        } catch (SQLException e) {
            System.err.println("SQL Error executing query: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
    }

    public static void executeMySQL(String s) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(s);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
