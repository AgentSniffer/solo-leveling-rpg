package cli;

import db.EmailDB;
import service.LoginService;
import utils.CliUtil;

public class LoginCli extends CliUtil {

    static LoginService authService = new LoginService();

    public static void display() {
        clearScreen();
        displayMenu("Arise, Hunter.\n1. 🔑 Login\n2. 📝 Register\n3. 🎨 Settings\n4. 🚪 Exit");

        String input = sc.nextLine().trim();
        resetColor();

        switch (input) {
            case "1" ->
                handleLogin();
            case "2" ->
                handleRegister();
            case "3" ->
                settings();
            case "4" -> {
                clearScreen();
                exit();
            }
            default -> {
                System.out.println(RED + "\nInvalid input" + RESET);
                pause();
                display();
            }
        }
    }

    public static void handleLogin() {
        clearScreen();
        printBox("Login (type 'back' to go back)");
        String email = promptForInput("Enter email: ");
        
        if (email.equals("back")) {
            display();
            return;
        }
        
        String password = promptForInput("Enter password: ");
        
        if (password.equals("back")) {
            display();
            return;
        }

        if (authService.login(email, password)) {
            System.out.println("\nLogin successful!");
            // Display game menu here
        } else {
            System.out.println("\nLogin failed. Invalid email or password.");
        }

        pause();
        display();
    }

    public static void handleRegister() {
        clearScreen();
        printBox("Register (type 'back' to go back)");

        // Email
        String email = promptForInput("Enter email: ");
        
        if (email.equals("back")) {
            display();
            return;
        }
        
                // Check if user already exists
        if (EmailDB.userExists(email)) {
            System.out.println("\nError: Email already exists. Please try logging in.");
            pause();
            display();
            return;
        }
        
        if (!LoginService.isValidEmail(email)) {
            System.out.println("\nError: Please enter a valid   Email (e.g., user@example.com)");
            pause();
            display();
            return;
        }

        // Password
        String password = promptForInput("Enter password: ");
        
        if (password.equals("back")) {
            display();
            return;
        }
        
        if (password.length() < 8) {
            System.out.println("\nError: Password must be at least 8 characters");
            pause();
            display();
            return;
        }

        if (authService.register(email, password)) {
            System.out.println("\n✅ Registration successful! You can now log in.");
        } else {
            System.out.println("\n❌ Error: Email already in use");
        }

        pause();
        display();
    }

    private static String promptForInput(String message) {
        System.out.print(message + DEFAULT_COLOR);
        String input = sc.nextLine().trim();
        resetColor();
        if (input.isEmpty()) {
            System.out.println("\nError: Input cannot be empty");
            pause();
            display();
        }
        return input;
    }
}
