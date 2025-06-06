package cli;

import db.EmailDB;
import service.LoginService;
import utils.CliUtil;

public class LoginCli extends CliUtil {

    static LoginService loginService = new LoginService();

    public static void display() {
        if (loginService == null) {
            System.err.println("Login service not available. Please check system config.");
            return;
        }

        while (true) {
            try {
                clearScreen();
                printBox("Arise, Hunter.\n1. 🔑 Login\n2. 📝 Register\n3. 🎨 Settings\n4. 🚪 Exit");

                String input = promptForInput("> ");
                resetColor();

                switch (input) {
                    case "1" -> {
                        handleLogin();
                        // After returning from game, the loop will show the main menu again
                    }
                    case "2" ->
                        handleRegister();
                    case "3" ->
                        settings();
                    case "4" -> {
                        clearScreen();
                        exit();
                        return;
                    }
                    default -> {
                        System.out.println(RED + "\n❌ Invalid input." + RESET);
                        pause();
                    }
                }
            } catch (Exception e) {
                System.err.println("Unexpected error in display(): " + e.getMessage());
                return;
            }
        }
    }

    private static boolean handleLogin() {
        while (true) {
            try {
                clearScreen();
                printBox("Login (type 'back' to go back)");

                String email = promptForInput("Enter email: ");
                if (email.equalsIgnoreCase("back")) {
                    return false;
                }

                String password = promptForInput("Enter password: ");
                if (password.equalsIgnoreCase("back")) {
                    return false;
                }

                if (loginService.login(email, password)) {
                    System.out.println(GREEN + "\n✅ Login successful!" + RESET);
                    pause();
                    GameCli.display();
                    return true;
                } else {
                    System.out.println(RED + "\n❌ Login failed. Invalid email or password." + RESET);
                    pause();
                }
            } catch (Exception e) {
                System.err.println("Error during login: " + e.getMessage());
                System.out.println(RED + "\n❌ An error occurred during login. Please try again." + RESET);
                pause();
                return false;
            }
        }
    }

    private static void handleRegister() {
        while (true) {
            try {
                clearScreen();
                printBox("Register (type 'back' to go back)");

                String email = promptForInput("Enter email: ");
                if (email.equalsIgnoreCase("back")) {
                    return;
                }

                if (!LoginService.isValidEmail(email)) {
                    System.out.println(RED + "\n❌ Invalid Email (e.g., user@example.com)" + RESET);
                    pause();
                    continue;
                }

                if (EmailDB.emailExists(email)) {
                    System.out.println(RED + "\n❌ Email already exists. Try logging in." + RESET);
                    pause();
                    return;
                }

                String password = promptForInput("Enter password: ");
                if (password.equalsIgnoreCase("back")) {
                    return;
                }

                if (password.length() < 8) {
                    System.out.println(RED + "\n❌ Password must be at least 8 characters" + RESET);
                    pause();
                    continue;
                }

                if (loginService.register(email, password)) {
                    System.out.println(GREEN + "\n✅ Registration successful! You can now log in." + RESET);
                } else {
                    System.out.println(RED + "\n❌ Registration failed. Please try again." + RESET);
                }

                pause();
                return;

            } catch (Exception e) {
                System.err.println("Error during registration: " + e.getMessage());
                System.out.println(RED + "\n❌ An error occurred during registration. Please try again." + RESET);
                pause();
                return;
            }
        }
    }

    private static String promptForInput(String message) {
        while (true) {
            try {
                System.out.print(message + DEFAULT_COLOR);
                String input = sc.nextLine().trim();
                resetColor();

                if (!input.isEmpty()) {
                    return input;
                }

                System.out.println(RED + "\n❌ Error: Input cannot be empty" + RESET);
                pause();
            } catch (Exception e) {
                System.err.println("Error reading input: " + e.getMessage());
                resetColor();
                return "back"; // fallback to exit path
            }
        }
    }
}
