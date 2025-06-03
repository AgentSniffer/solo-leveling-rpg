package ui;

import models.User;
import db.GameDB;

/**
 * Simple test class to verify UI components
 */
public class UITest {
    public static void main(String[] args) {
        System.out.println("Starting UI Test...");
        
        try {
            // Initialize the database
            GameDB.ensureDatabaseExists();
            GameDB.initDB();
            
            // Initialize the UI manager
            UIManager uiManager = UIManager.getInstance();
            
            // For testing, create a test user if it doesn't exist
            String testUser = "test";
            String testPass = "test123";
            
            if (!GameDB.userExists(testUser)) {
                System.out.println("Creating test user...");
                GameDB.createUser(testUser, testPass);
            }
            
            // Start with the login screen
            uiManager.showLogin();
            
            // The UIManager will handle the rest of the flow
            uiManager.start();
        } catch (Exception e) {
            System.err.println("UI Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
