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
                System.out.println("▶️  Starting your adventure");
                Display.delay(2);
                Display.clear();
                Game.startGame();
                return true;
            case 2:
                System.out.println("📂 Loading your saved journey");
                Display.delay(2);
                Display.clear();
                return true;
            case 3:
                System.out.println("⚙️  Adjusting your experience");
                Display.delay(2);
                Display.clear();
                return true;
            case 4:
                System.out.println("🔐 Entering the gate");
                Display.delay(2);
                Display.clear();
                return true;
            case 5:
                System.out.println("📝 Joining the hunter's guild");
                Display.delay(2);
                Display.clear();
                return true;
            case 6:
                System.out.println("👋 Until we meet again, hunter. Arise!");
                Display.delay(2);
                System.exit(0);
                return false;
            default:
                return true;
        }
    }
}
