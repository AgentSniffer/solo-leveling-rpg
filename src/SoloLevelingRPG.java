
// Main game class - controls everything
public class SoloLevelingRPG {

    public static void main(String[] args) {

        MainMenu.show();

        int choice = UI.getMenuInput(6, Messages.MENU_OPTIONS);  // Already handles invalid input internally
        System.out.printf(Messages.MENU_SUCCESS_CHOICE + "%n", choice);
        MainMenu.handleChoice(choice);
    }
}
