package ui;

import db.GameDB;
import game.GameManager;
import java.io.IOException;
import java.util.Scanner;
import models.PlayerData;
import models.User;

public class GameUI {

    static final String ADMIN_PASSWORD = "admin123";
    static Scanner scanner = new Scanner(System.in);
    static boolean running = true;
    static User user = null;
    static PlayerData player = null;

    public static void showLoginMenu() {
        while (true) {
            clearScreen();
            printBox("LOGIN / REGISTER\n1. Login\n2. Register\n3. Exit");
            System.out.print("> ");

            String input = scanner.nextLine();

            switch (input.trim()) {
                case "1" -> {
                    if (login()) {
                        showMainMenu();
                    }
                }
                case "2" ->
                    register();
                case "3" -> {
                    return;
                }
                default -> {
                    System.out.println("\nInvalid choice. Try again");
                    waitForInput();
                }
            }
        }
    }

    static boolean login() {
        clearScreen();
        printBox("LOGIN");
        System.out.print("Username: ");
        String name = scanner.nextLine().trim();
        System.out.print("Password: ");
        String pass = scanner.nextLine().trim();

        User authenticatedUser = GameDB.authenticateUser(name, pass);
        if (authenticatedUser != null) {
            user = authenticatedUser;
            player = GameDB.loadGameState(user);
            System.out.println("\nLogin successful!");
            waitForInput();
            return true;
        }

        System.out.println("\nInvalid credentials");
        waitForInput();
        return false;
    }

    static void register() {
        clearScreen();
        printBox("REGISTER");

        String name;
        while (true) {
            System.out.print("Choose username: ");
            name = scanner.nextLine().trim();
            if (name.length() < 3) {
                System.out.println("Min 3 chars");
                continue;
            }
            if (GameDB.userExists(name)) {
                System.out.println("Name taken");
            } else {
                break;
            }
        }

        String pass;
        while (true) {
            System.out.print("Choose password: ");
            pass = scanner.nextLine().trim();
            if (pass.length() < 6) {
                System.out.println("Min 6 chars");
            } else {
                break;
            }
        }

        if (GameDB.createUser(name, pass)) {
            user = new User(name, pass);
            player = new PlayerData(name);
            GameDB.saveGameState(user, player);
            System.out.println("\nAccount created! Logged in.");
            waitForInput();
        }
    }

    static void showMainMenu() {
        while (running) {
            clearScreen();
            printBox("SOLO LEVELING GAME\n1. New Game\n2. Load Game\n3. Options\n4. Admin\n5. Exit");
            System.out.print("> ");

            String input = scanner.nextLine();

            switch (input.trim()) {
                case "1" ->
                    startNewGame();
                case "2" ->
                    loadGame();
                case "3" ->
                    showOptions();
                case "4" -> {
                    System.out.print("\nEnter admin password: ");
                    if (scanner.nextLine().equals(ADMIN_PASSWORD)) {
                        showAdminMenu();
                    } else {
                        System.out.println("\nInvalid password!");
                        waitForInput();
                    }
                }
                case "5" -> {
                    System.out.println("\nBye!");
                    running = false;
                }
                default -> {
                    System.out.println("\nInvalid");
                    waitForInput();
                }
            }
        }
    }

    static void startNewGame() {
        if (user == null) {
            System.out.println("\nLogin required!");
            waitForInput();
            return;
        }

        clearScreen();
        printBox("NEW GAME");
        
        System.out.print("Character name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Player";
        }
        
        // Create player with default values
        player = new PlayerData(name);
        
        // Role selection
        clearScreen();
        printBox("SELECT ROLE\n1. Hunter - Increased damage\n2. Tank - More HP\n3. Healer - HP regeneration");
        System.out.print("> ");
        String roleChoice = scanner.nextLine().trim();
        
        switch (roleChoice) {
            case "1" -> {
                player.setRole("Hunter");
                System.out.println("\nYou selected Hunter! Your attacks deal more damage.");
            }
            case "2" -> {
                player.setRole("Tank");
                player.setMaxHealth(150);  // Tanks get more health
                player.setHealth(150);
                System.out.println("\nYou selected Tank! You have more health.");
            }
            case "3" -> {
                player.setRole("Healer");
                System.out.println("\nYou selected Healer! You regenerate health after battles.");
            }
            default -> {
                player.setRole("Adventurer");  // Default role
                System.out.println("\nYou selected Adventurer! Jack of all trades.");
            }
        }
        
        waitForInput();
        
        // Save the initial player state
        GameDB.saveGameState(user, player);
        playGame();
    }

    static void loadGame() {
        if (user == null) {
            System.out.println("\nLogin required!");
            waitForInput();
            return;
        }

        PlayerData loadedPlayer = GameDB.loadGameState(user);
        if (loadedPlayer != null) {
            player = loadedPlayer;
            playGame();
        } else {
            System.out.println("\nNo saves found!");
            waitForInput();
        }
    }

    // Game manager instance
    static GameManager gameManager;
    
    static void playGame() {
        // Initialize game manager if needed
        if (gameManager == null) {
            gameManager = new GameManager(player, user, scanner);
        }
        
        while (true) {
            clearScreen();
            System.out.println(player.getDetailedStatus());
            printBox("ACTIONS\n1. Enter Dungeon\n2. View Character\n3. Save & Return to Menu");
            System.out.print("> ");

            String input = scanner.nextLine();
            switch (input.trim()) {
                case "1" -> {
                    clearScreen();
                    // Let game manager handle dungeon selection and combat
                    gameManager.selectDungeon();
                    waitForInput();
                }
                case "2" -> {
                    clearScreen();
                    printBox(player.getDetailedStatus());
                    waitForInput();
                }
                case "3" -> {
                    // Save game state and player progress
                    gameManager.saveProgress();
                    return;
                }
                default -> {
                    System.out.println("\nInvalid choice!");
                    waitForInput();
                }
            }
        }
    }

    static void showOptions() {
        while (true) {
            clearScreen();
            printBox("OPTIONS\n1. Toggle Sound\n2. Change Difficulty\n3. Back");
            System.out.print("> ");
            String input = scanner.nextLine();
            switch (input.trim()) {
                case "1" -> {
                    System.out.println("\nSound toggled!");
                    waitForInput();
                }
                case "2" -> {
                    System.out.println("\nDifficulty changed!");
                    waitForInput();
                }
                case "3" -> {
                    return;
                }
                default -> {
                    System.out.println("\nInvalid");
                    waitForInput();
                }
            }
        }
    }

    static void showAdminMenu() {
        while (true) {
            clearScreen();
            printBox("ADMIN MENU\n1. List All Users\n2. Reset Database\n3. MySQL Queries\n4. Back to Main Menu");
            System.out.print("> ");

            switch (scanner.nextLine().trim()) {
                case "1" -> {
                    GameDB.listAllUsers();
                    waitForInput();
                }
                case "2" -> {
                    System.out.print("\nWARNING: This will delete ALL data! Type 'CONFIRM' to continue: ");
                    if (scanner.nextLine().equals("CONFIRM")) {
                        System.out.print("Enter admin password: ");
                        if (scanner.nextLine().equals(ADMIN_PASSWORD)) {
                            GameDB.resetDatabase();
                        } else {
                            System.out.println("Invalid password!");
                        }
                        waitForInput();
                    }
                }
                case "3" ->
                    runSqlQueryMenu();
                case "4" -> {
                    return;
                }
                default -> {
                    System.out.println("\nInvalid choice!");
                    waitForInput();
                }
            }
        }
    }

    static void runSqlQueryMenu() {
        while (true) {
            clearScreen();
            printBox("RUN SQL QUERY (type 'back' to return)");
            System.out.print("MySQL [solo_leveling_user]> ");

            String query = scanner.nextLine().trim();
            if (query.equalsIgnoreCase("back")) {
                return;
            }

            if (!query.trim().isEmpty()) {
                GameDB.runSqlQuery(query);
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    static void clearScreen() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
        }
    }

    static void waitForInput() {
        System.out.println("\nPress Enter...");
        scanner.nextLine();
    }

    public static void printBox(String text, int marginLeft, int marginRight, int marginTop, int marginBottom) {
        String[] textLines = text.split("\n");

        // Find the longest line
        int maxLineLength = 0;
        for (String line : textLines) {
            if (line.length() > maxLineLength) {
                maxLineLength = line.length();
            }
        }

        int totalWidth = maxLineLength + marginLeft + marginRight;

        // Create horizontal line
        String horizontalLine = "";
        for (int i = 0; i < totalWidth; i++) {
            horizontalLine += "═";
        }

        // Print top border
        System.out.println("╔" + horizontalLine + "╗");

        // Print top vertical padding
        for (int i = 0; i < marginTop; i++) {
            System.out.print("║");
            for (int j = 0; j < totalWidth; j++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }

        // Print text lines with margins
        for (String line : textLines) {
            int rightPadding = maxLineLength - line.length();

            System.out.print("║");

            for (int i = 0; i < marginLeft; i++) {
                System.out.print(" ");
            }

            System.out.print(line);

            for (int i = 0; i < rightPadding + marginRight; i++) {
                System.out.print(" ");
            }

            System.out.println("║");
        }

        // Print bottom vertical padding
        for (int i = 0; i < marginBottom; i++) {
            System.out.print("║");
            for (int j = 0; j < totalWidth; j++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }

        // Print bottom border
        System.out.println("╚" + horizontalLine + "╝");
    }

    // method overloading default padding
    public static void printBox(String text) {
        printBox(text, 1, 1, 0, 0);
    }
}
