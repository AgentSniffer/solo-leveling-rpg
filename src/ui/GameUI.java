package ui;

import db.GameDB;
import java.util.Scanner;
import models.User;
import models.PlayerData;

public class GameUI {

    static final String ADMIN_PASSWORD = "admin123";
    static Scanner scanner = new Scanner(System.in);
    static boolean running = true;
    static User user = null;
    static PlayerData player = null;

    public static void showLoginMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== LOGIN / REGISTER ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice: ");

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
        System.out.println("=== LOGIN ===");
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
        System.out.println("=== REGISTER ===");

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
            System.out.println("=== SOLO LEVELING GAME ===");
            System.out.println("1. New Game");
            System.out.println("2. Load Game");
            System.out.println("3. Options");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.print("\nEnter choice: ");

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
        System.out.println("=== NEW GAME ===");
        System.out.print("Character name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Player";
        }

        player = new PlayerData(name);
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

    static void playGame() {
        while (true) {
            clearScreen();
            System.out.println(player.getStatus());
            System.out.println("1. Hunt Monsters (Gain EXP)");
            System.out.println("2. Save & Return to Menu");
            System.out.print("\nChoose an action: ");

            String input = scanner.nextLine();
            switch (input.trim()) {
                case "1" -> {
                    int expGained = 10 + (int) (Math.random() * 20);
                    System.out.println("\nYou gained " + expGained + " EXP!");
                    player.gainExp(expGained);
                    waitForInput();
                }
                case "2" -> {
                    GameDB.saveGameState(user, player);
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
            System.out.println("=== OPTIONS ===");
            System.out.println("1. Toggle Sound");
            System.out.println("2. Change Difficulty");
            System.out.println("3. Back");
            System.out.print("\nEnter choice: ");

            switch (scanner.nextLine().trim()) {
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
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. List All Users");
            System.out.println("2. Reset Database");
            System.out.println("3. MySQL Queries");
            System.out.println("4. Back to Main Menu");
            System.out.print("\nEnter choice: ");

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
            System.out.println("=== RUN SQL QUERY (type 'back' to return) ===");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void waitForInput() {
        System.out.println("\nPress Enter...");
        scanner.nextLine();
    }
}
