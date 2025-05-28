
public class test {
    public static void main(String[] args) {
        Display.printBox("1. Start New Game\n"
            + "2. Load Game\n"
            + "3. Options\n"
            + "4. Login\n"
            + "5. Register\n"
            + "6. Exit");  // print the menu inside the box
        System.out.print("> ");
        UI.waitForEnter();
    }
}