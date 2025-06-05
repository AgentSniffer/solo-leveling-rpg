package service;

import java.util.HashMap;
import java.util.UUID;

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

        String salt = EmailDB.getSaltForUser(email); // new method
        if (salt == null) {
            System.out.println("\nError: User not found.");
            return false;
        }

        String correctHash = EmailDB.getUserHash(email);
        String attemptedHash = HashUtil.hashPassword(password, salt);

        return attemptedHash.equals(correctHash);
    }

    public boolean register(String email, String password) {
        if (isInputInvalid(email, password) || !isValidEmail(email) || password.length() < 8) {
            System.out.println("\nError: Invalid email or password.");
            return false;
        }

        if (EmailDB.emailExists(email)) {
            System.out.println("\nError: Email already exists.");
            return false;
        }

        String salt = UUID.randomUUID().toString(); // random salt
        String hashedPassword = HashUtil.hashPassword(password, salt);

        if (EmailDB.createEmail(email, hashedPassword, salt)) {
            users.put(email, new UserModel(email, hashedPassword, salt));
            return true;
        }

        System.out.println("\nError: Failed to create user in the database.");
        return false;
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
        return !(parts.length != 2 || !isDomainValid(parts[1]));
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

    public static void logout() {
        // Clear any session data or user state here
        System.out.println("\nYou have been successfully logged out.");
    }
}
