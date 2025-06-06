package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtil;
import utils.HashUtil;

public class EmailDB extends DBUtil {

    public static boolean checkEmailPassword(String email, String password) {
        try {
            String query = "SELECT password_hash, salt FROM users WHERE email = '" + email + "'";
            ResultSet rs = executeQuery(query);

            if (rs != null && rs.next()) {
                String storedHash = rs.getString("password_hash");
                String salt = rs.getString("salt"); // Get the salt from the database

                String inputHash = HashUtil.hashPassword(password, salt); // Recreate the hash using the same salt

                return storedHash.equals(inputHash);
            } else {
                return false; // Email not found
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static boolean emailExists(String email) {
        try {
            String query = "SELECT * FROM users WHERE email = '" + email + "'";
            ResultSet rs = executeQuery(query);
            return rs != null && rs.next();
        } catch (SQLException e) {
            System.out.println("SQL Error checking user: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error checking user: " + e.getMessage());
            return false;
        }
    }

    public static boolean createEmail(String email, String hashedPassword, String salt) {
        try {
            String query = "INSERT INTO users (email, password_hash, salt) VALUES ('" + email + "', '" + hashedPassword + "', '" + salt + "')";
            executeMySQL(query);
            return true;
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            return false;
        }
    }

    public static String getUserHash(String email) {
        try {
            String query = "SELECT password_hash FROM users WHERE email = '" + email + "'";
            ResultSet rs = executeQuery(query);
            if (rs != null && rs.next()) {
                return rs.getString("password_hash");
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

    public static String getSaltForUser(String email) {
        try {
            String query = "SELECT salt FROM users WHERE email = '" + email + "'";
            ResultSet rs = executeQuery(query);
            if (rs != null && rs.next()) {
                return rs.getString("salt");
            }
            return null;
        } catch (SQLException e) {
            System.out.println("SQL Error getting salt: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error getting salt: " + e.getMessage());
            return null;
        }
    }
}
