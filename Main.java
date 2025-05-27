
public class Main {

    public static void main(String[] args) {
        Display.clear();
        Display.logo();
        Display.showWelcomeScreen();
        UI.waitForEnter();
        Display.clear();
        Display.printBox("1. Start New Game\n2. Load Game\n3. Options\n4. Login\n5. Register\n6. Exit");
        UI.waitForEnter();
        Display.clear();
    }
}
