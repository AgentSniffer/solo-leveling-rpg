
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static boolean isRunning = true;

    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        while (isRunning) {
            clearScreen();
            System.out.println("=== SOLO LEVELING GAME ===");
            System.out.println("1. New Game");
            System.out.println("2. Load Game");
            System.out.println("3. Options");
            System.out.println("4. Exit");
            System.out.print("\nEnter your choice: ");

            String input = scanner.nextLine();
            handleMenuInput(input);
        }
    }

    private static void handleMenuInput(String input) {
        switch (input.trim()) {
            case "1" ->
                startNewGame();
            case "2" ->
                loadGame();
            case "3" ->
                showOptions();
            case "4" ->
                exitGame();
            default -> {
                System.out.println("Invalid input. Please try again.");
                waitForInput();
            }
        }
    }

    private static void startNewGame() {
        clearScreen();
        System.out.println("Starting a new game...");
        // TODO: Add new game logic here
        waitForInput();
    }

    private static void loadGame() {
        clearScreen();
        System.out.println("Loading game...");
        // TODO: Add load game logic here
        waitForInput();
    }

    private static void showOptions() {
        clearScreen();
        System.out.println("=== GAME OPTIONS ===");
        System.out.println("1. Toggle Sound");
        System.out.println("2. Change Difficulty");
        System.out.println("3. Back to Main Menu");

        String input = scanner.nextLine();
        if (!input.equals("3")) {
            System.out.println("Options functionality coming soon!");
            waitForInput();
        }
    }

    private static void exitGame() {
        clearScreen();
        System.out.println("Thank you for playing!");
        isRunning = false;
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void waitForInput() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
