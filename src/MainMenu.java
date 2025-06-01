// ─────────────────────────────────────────────────────────────
// ▼ MainMenu - Handles the Main Game Menu
// ─────────────────────────────────────────────────────────────
public class MainMenu {

    // ─────────────────────────────────────────────────────────────
    // ▼ Show Main Menu Intro Screen
    // ─────────────────────────────────────────────────────────────

    public static void showWelcomeMenu() {
        Display.clear();
        Display.logo();
        Display.delay(2);
        Display.showWelcomeScreen();
        UI.waitForEnter();
        Display.clear();
    }        

    // ─────────────────────────────────────────────────────────────
    // ▼ Handle Menu Selection
    // ─────────────────────────────────────────────────────────────

    public static boolean handleMenuChoice(int choice) {
        Display.clear();

        switch (choice) {
            case 1:
                System.out.println(Messages.ACTION_START_GAME);
                Display.delay(2);
                Display.clear();
                Game.startGame();
                return true;
            case 2:
                System.out.println(Messages.ACTION_LOAD_GAME);
                Display.delay(2);
                Display.clear();
                return true;
            case 3:
                System.out.println(Messages.ACTION_OPTIONS);
                Display.delay(2);
                Display.clear();
                return true;
            case 4:
                System.out.println(Messages.ACTION_LOGIN);
                Display.delay(2);
                Display.clear();
                return true;
            case 5:
                System.out.println(Messages.ACTION_REGISTER);
                Display.delay(2);
                Display.clear();
                return true;
            case 6:
                System.out.println(Messages.ACTION_EXIT);
                Display.delay(2);
                System.exit(0);
                return false;
            default:
                return true;
        }
    }
}
