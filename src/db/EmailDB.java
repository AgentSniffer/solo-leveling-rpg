package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtil;

public class EmailDB extends DBUtil {

    public static boolean userExists(String email) {
        try {
            String query = "SELECT id FROM users WHERE email = '" + email + "'";
            ResultSet rs = executeQuery(query);
            return rs != null && rs.next();
        } catch (SQLException e) {
            System.out.println("SQL Error checking user: " + e.getMessage());
            return false;
        }  catch (Exception e) {
            System.out.println("Error checking user: " + e.getMessage());
            return false;
        }
    }

    public static boolean createUser(String email, String hashedPassword) {
        try {
            String query = "INSERT INTO users (email, hashed_password) VALUES ('"
                    + email + "', '" + hashedPassword + "')";
            executeMySQL(query);
            return true;
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            return false;
        }
    }

    public static String getUserHash(String email) {
        try {
            String query = "SELECT hashed_password FROM users WHERE email = '" + email + "'";
            ResultSet rs = executeQuery(query);
            if (rs != null && rs.next()) {
                return rs.getString("hashed_password");
            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQL Error getting user hash: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error getting user hash: " + e.getMessage());
            return null;
        }
    }
}
