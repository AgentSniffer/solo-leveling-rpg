package ui;

import models.User;

/**
 * Manages the navigation between different UI screens in the game.
 */
public class UIManager {
    private static UIManager instance;
    private BaseCLI currentUI;
    
    private final LoginUI loginUI;
    private final MainMenuUI mainMenuUI;
    private GameUI gameUI;
    private User currentUser;
    
    public UIManager() {
        // Initialize login and main menu UIs
        this.loginUI = new LoginUI(this);
        this.mainMenuUI = new MainMenuUI(this);
        this.gameUI = null; // Will be initialized when needed
        this.currentUser = null;
        
        // Start with login screen
        this.currentUI = loginUI;
    }
    
    /**
     * Gets the singleton instance of UIManager
     */
    public static synchronized UIManager getInstance() {
        if (instance == null) {
            instance = new UIManager();
        }
        return instance;
    }
    
    /**
     * Gets the currently logged-in user
     * @return The current User or null if not logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Sets the current user after successful login
     * @param user The user to set as current
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    /**
     * Switches to the login screen
     */
    public void showLogin() {
        currentUI = loginUI;
        currentUI.display();
    }
    
    /**
     * Switches to the main menu
     */
    public void showMainMenu() {
        currentUI = mainMenuUI;
        currentUI.display();
    }
    
    /**
     * Switches to the game UI
     */
    public void showGame() {
        if (gameUI == null) {
            if (currentUser == null) {
                System.err.println("Error: No user logged in");
                return;
            }
            gameUI = new GameUI(this);
        }
        currentUI = gameUI;
        currentUI.display();
    }
    
    /**
     * Starts the UI manager and shows the current screen
     */
    public void start() {
        while (true) {
            currentUI.run();
        }
    }
    
    /**
     * Exits the application
     */
    public void exit() {
        System.out.println("Thank you for playing!");
        System.exit(0);
    }
}
