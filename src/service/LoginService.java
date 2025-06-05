package service;

import java.util.HashMap;

import db.EmailDB;
import utils.DBUtil;
import utils.HashUtil;
import model.UserModel;

public class LoginService extends DBUtil {

    static HashMap<String, UserModel> users = new HashMap<String, UserModel>();

    public boolean login(String email, String password) {
        if (isInputInvalid(email, password)) {
            return false;
        }

        String hashedPassword = EmailDB.getUserHash(email);
        if (hashedPassword == null) {
            System.out.println("\nError: User does not exist.");
            return false; // User does not exist
        }

        return HashUtil.hashPassword(password).equals(hashedPassword);
    }

    public boolean register(String email, String password) {
        if (isInputInvalid(email, password) || !isValidEmail(email) || password.length() < 8) {
            System.out.println("\nError: Invalid email or password.");
            return false;
        }

        if (EmailDB.userExists(email)) {
            System.out.println("\nError: Email already exists.");
            return false; // User already exists
        }

        String hashedPassword = HashUtil.hashPassword(password);
        if (EmailDB.createUser(email, hashedPassword)) {
            users.put(email, new UserModel(email, hashedPassword));
            return true;
        }
        System.out.println("\nError: Failed to create user in the database.");
        return false; // Failed to create user in the database
    }

    private boolean isInputInvalid(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            System.out.println("\nError: Email and password cannot be empty.");
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.length() < 6) {
            return false;
        }

        String[] parts = email.split("@", 2);
        if (parts.length != 2 || !isDomainValid(parts[1])) {
            return false;
        }
        return true;
    }

    private static boolean isDomainValid(String domain) {
        if (!domain.contains(".") || domain.startsWith(".") || domain.endsWith(".")) {
            return false;
        }

        String[] domainParts = domain.split("\\.");
        for (String part : domainParts) {
            if (part.isEmpty() || part.length() < 2) {
                return false;
            }
        }
        return true;
    }
}
