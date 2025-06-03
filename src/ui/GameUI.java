package ui;

import game.GameManager;
import models.PlayerData;
import models.User;
import java.util.Scanner;

/**
 * Handles the main game UI and gameplay
 */
public class GameUI extends BaseCLI {
    private final UIManager uiManager;
    private final GameManager gameManager;
    private final User user;
    private final PlayerData player;
    private final Scanner scanner;
    
    // Game state
    private boolean running;

    public GameUI(UIManager uiManager) {
        super();
        this.uiManager = uiManager;
        this.user = uiManager.getCurrentUser();
        this.scanner = new Scanner(System.in);
        this.player = new PlayerData(user.username);
        this.gameManager = new GameManager(player, user, scanner);
        this.running = true;
    }

    @Override
    public void display() {
        if (gameManager.isRecovering()) {
            showRecoveryScreen();
            return;
        }
        
        clearScreen();
        printBox("DUNGEON SELECT");
        System.out.println(gameManager.getDungeonProgress());
        System.out.println("\n1. Select Dungeon");
        System.out.println("2. Character Info");
        System.out.println("3. Save Game");
        System.out.println("4. Return to Main Menu");
        System.out.print("> ");
    }
    
    @Override
    public void handleInput() {
        String input = scanner.nextLine().trim();
        
        switch (input) {
            case "1" -> selectDungeon();
            case "2" -> showCharacterInfo();
            case "3" -> saveGame();
            case "4" -> uiManager.showMainMenu();
            default -> {
                System.out.println("\nInvalid choice. Try again");
                waitForInput();
            }
        }
    }
    
    private void selectDungeon() {
        gameManager.selectDungeon();
    }
    
    private void showCharacterInfo() {
        clearScreen();
        printBox("CHARACTER INFO\n\n" + player);
        waitForInput();
    }
    
    private void saveGame() {
        gameManager.saveProgress();
        waitForInput();
    }
    
    private void showRecoveryScreen() {
        clearScreen();
        printBox("RECOVERY");
        System.out.println("You are currently recovering from your last defeat.");
        System.out.println("Time remaining: " + gameManager.getRecoveryTimeRemaining() + " seconds");
        System.out.println("\n1. Wait to recover");
        System.out.println("2. Use recovery item (if available)");
        System.out.println("3. Return to main menu");
        System.out.print("> ");
        
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1" -> {
                System.out.println("\nWaiting to recover...");
                waitForInput();
            }
            case "2" -> {
                System.out.println("\nNo recovery items available!");
                waitForInput();
            }
            case "3" -> uiManager.showMainMenu();
            default -> {
                System.out.println("\nInvalid choice.");
                waitForInput();
            }
        }
    }
}
