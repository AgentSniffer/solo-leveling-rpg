
// Main game class - calls everything Arisee.....
public class SoloLevelingRPG {

    public static void main(String[] args) {
        MainMenu.showWelcomeMenu();
        // ======================================
        boolean running = true;
        while (running) {
            int choice = UI.getMenuInput(6, Messages.MENU_OPTIONS, Messages.ASCII_GAME_LOGO);
            running = MainMenu.handleMenuChoice(choice); // Returns false when player wants to exit
        }
        // ======================================
        UI.uiInput.close(); // Close scanner when completely done
    }
}
