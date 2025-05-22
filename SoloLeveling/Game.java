package SoloLeveling;

public class Game {
    public static void main(String[] args) {
        Menu.clearScreen();
        Menu.showWelcomeScreen();

        //System.out.println(System.getProperty("os.name")); //just for testing

        boolean isRunning = true;
        while (isRunning) {
            Menu.showMainMenu();
        }
    }
}