package cli;

import service.GameService;
import service.LoginService;
import utils.CliUtil;

public class GameCli extends CliUtil {
    static GameService SoloLevelingRPG = new GameService();

    public static void display() {
        clearScreen();
        
        printBox("1. Load Game\n2. New Game\n3. Logout\n4. Back");
        String input = promptForInput("> ");
        resetColor();

        switch (input) {
            case "1" -> {
                System.out.println("Load Game");
            }
            case "2" -> {
                SoloLevelingRPG.startGame();
            }
            case "3" -> {
                handleLogout();
            }
            case "4" -> {
                LoginCli.display();
            }
            default -> {
                System.out.println(RED + "\nInvalid input" + RESET);
                pause();
                display();
            }
        }

    }

    private static void handleLogout() {
        LoginService.logout();
        pause();
        LoginCli.display();
    }

    public static String promptForInput(String message) {
        System.out.print(message + DEFAULT_COLOR);
        String input = sc.nextLine().trim();
        resetColor();
        if (input.isEmpty()) {
            System.out.println("\nError: Input cannot be empty");
            pause();
            display();
        }
        return input;
    }
}
