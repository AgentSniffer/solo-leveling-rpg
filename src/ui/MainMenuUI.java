package ui;

/**
 * Handles the main menu UI and navigation
 */
public class MainMenuUI extends BaseCLI {
    private final UIManager uiManager;
    
    public MainMenuUI(UIManager uiManager) {
        super();
        this.uiManager = uiManager;
    }
    
    @Override
    public void display() {
        clearScreen();
        printBox("MAIN MENU\n1. Play Game\n2. Character Info\n3. Settings\n4. Logout\n5. Exit");
        System.out.print("> ");
    }
    
    @Override
    public void handleInput() {
        String input = scanner.nextLine().trim();
        
        switch (input) {
            case "1" -> uiManager.showGame();
            case "2" -> showCharacterInfo();
            case "3" -> showSettings();
            case "4" -> {
                System.out.println("\nLogging out...");
                waitForInput();
                uiManager.showLogin();
            }
            case "5" -> uiManager.exit();
            default -> {
                System.out.println("\nInvalid choice. Try again");
                waitForInput();
            }
        }
    }
    
    private void showCharacterInfo() {
        clearScreen();
        printBox("CHARACTER INFO\n\nThis feature is coming soon!");
        waitForInput();
    }
    
    private void showSettings() {
        clearScreen();
        printBox("SETTINGS\n\nThis feature is coming soon!");
        waitForInput();
    }
}
