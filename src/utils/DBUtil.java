package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {

    static Connection conn;
    static Statement stmt;
    static String DB_URL;
    static String DB_USER;
    static String DB_PASSWORD;

    public static void loadDatabaseConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("db.properties")) {
            properties.load(input);
            DB_URL = properties.getProperty("db.url");
            DB_USER = properties.getProperty("db.user");
            DB_PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void connect() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("SQL Error connecting to database: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    public static void executeMySQL(String s) {
        try {
            stmt = conn.createStatement();
            stmt.execute(s);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static ResultSet executeQuery(String query) {
        try {
            stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Query Error: " + e.getMessage());
            return null;
        }
    }
}
