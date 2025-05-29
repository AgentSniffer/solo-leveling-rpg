
// Simple MainMenu class - manages game menus
public class MainMenu {

    public static void show() {
        Display.clear();
        Display.logo();
        Display.delay(2);
        Display.showWelcomeScreen();
        UI.waitForEnter();
        Display.clear();
    }

    public static void handleChoice(int choice) {
        Display.clear();
        switch (choice) {
            case 1:
                System.out.println(Messages.CHOICE_START_GAME);
                Display.delay(2);
                break;
            case 2:
                System.out.println(Messages.CHOICE_LOAD_GAME);
                Display.delay(2);
                break;
            case 3:
                System.out.println(Messages.CHOICE_OPTIONS);
                Display.delay(2);
                break;
            case 4:
                System.out.println(Messages.CHOICE_LOGIN);
                Display.delay(2);
                break;
            case 5:
                System.out.println(Messages.CHOICE_REGISTER);
                Display.delay(2);
                break;
            case 6:
                System.out.println(Messages.CHOICE_EXIT);
                Display.delay(2);
                System.exit(0);
                break;
        }
    }
}
