package cli;

import service.GameService;
import utils.CliUtil;

public class GameCli extends CliUtil {

    static GameService SoloLevelingRPG = new GameService();

    public static void display() {
        while (true) {
            clearScreen();

            printBox("1. Load Game\n2. New Game\n3. Back to Menu");
            String input = promptForInput("> ");
            if (input == null) {
                return;
            }
            resetColor();

            switch (input) {
                case "1" -> {
                    SoloLevelingRPG.loadGame("username");
                }
                case "2" -> {
                    SoloLevelingRPG.startGame();
                }
                case "3" -> {
                    System.out.println(GREEN + "\nReturning to main menu..." + RESET);
                    pause();
                    return;
                }
                default -> {
                    System.out.println(RED + "\n❌ Invalid input. Try again." + RESET);
                    pause();
                    clearScreen();
                }
            }
        }
    }

    public static String promptForInput(String message) {
        while (true) {
            System.out.print(message + DEFAULT_COLOR);

            String input = sc.nextLine();
            resetColor();

            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }

            System.out.println(RED + "\n❌ Error: Input cannot be empty." + RESET);
            pause();
        }
    }

}
