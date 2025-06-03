package ui;

import db.GameDB;
import models.User;

/**
 * Handles the login and registration UI
 */
public class LoginUI extends BaseCLI {
    private final UIManager uiManager;
    public LoginUI(UIManager uiManager) {
        super();
        this.uiManager = uiManager;
    }
    
    @Override
    public void display() {
        clearScreen();
        printBox("LOGIN / REGISTER\n1. Login\n2. Register\n3. Exit");
        System.out.print("> ");
    }
    
    @Override
    public void handleInput() {
        String input = scanner.nextLine().trim();
        
        switch (input) {
            case "1" -> login();
            case "2" -> register();
            case "3" -> uiManager.exit();
            default -> {
                System.out.println("\nInvalid choice. Try again");
                waitForInput();
            }
        }
    }
    
    private void login() {
        clearScreen();
        printBox("LOGIN");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        User user = GameDB.authenticateUser(username, password);
        if (user != null) {
            System.out.println("\nLogin successful!");
            uiManager.setCurrentUser(user);
            waitForInput();
            uiManager.showMainMenu();
        } else {
            System.out.println("\nInvalid username or password!");
            waitForInput();
        }
    }
    
    private void register() {
        clearScreen();
        printBox("REGISTER");
        System.out.print("Choose a username: ");
        String username = scanner.nextLine().trim();
        
        if (username.isEmpty()) {
            System.out.println("\nUsername cannot be empty!");
            waitForInput();
            return;
        }
        
        System.out.print("Choose a password: ");
        String password = scanner.nextLine().trim();
        
        if (password.length() < 4) {
            System.out.println("\nPassword must be at least 4 characters long!");
            waitForInput();
            return;
        }
        
        if (GameDB.createUser(username, password)) {
            System.out.println("\nRegistration successful! Please log in.");
            waitForInput();
        } else {
            System.out.println("\nUsername already exists!");
            waitForInput();
        }
    }
}
