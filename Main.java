
public class Main {

    public static void main(String[] args) {

        // Initial display
        Display.clear();
        Display.logo();
        Display.showWelcomeScreen();
        UI.waitForEnter();

        // Main menu loop
        int choice;
        do {
            Display.clear();
            Display.showMenu();
            choice = UI.getMenuInput(6);

            // pause if input is invalid
            if (choice == -1) {
                System.out.println("Press Enter to continue...");
                UI.waitForEnter();
            }

        } while (choice == -1);

        // Menu choice
        // Enhanced switch statement better than switch
        // statement in Java 12 and above
        switch (choice) {
            case 1 -> startNewGame();
            case 2 -> loadGame();
            case 3 -> showOptions();
            case 4 -> login();
            case 5 -> register();
            case 6 -> System.exit(0);
        }
    }

    private static void startNewGame() {
        // TODO: Implement new game logic
        System.out.println("Starting new game...");
        Display.delay(1);
    }

    private static void loadGame() {
        // TODO: Implement load game logic
        System.out.println("Loading game...");
        Display.delay(1);
    }

    private static void showOptions() {
        // TODO: Implement options menu
        System.out.println("Showing options...");
        Display.delay(1);
    }

    private static void login() {
        // TODO: Implement login logic
        System.out.println("Logging in...");
        Display.delay(1);
    }

    private static void register() {
        // TODO: Implement registration logic
        System.out.println("Registering new account...");
        Display.delay(1);
    }
}
