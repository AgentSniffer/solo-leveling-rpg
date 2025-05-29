
import java.util.InputMismatchException;
import java.util.Scanner;

// Simple UI class - makes the game talk to you
public class UI {

    static Scanner uiInput = new Scanner(System.in);

    public static void waitForEnter() {
        System.out.println("📌 SYSTEM: Press [Enter] to continue...");
        uiInput.nextLine();
    }

    // Handles invalid input errors specifically for menu input
    public static void handleMenuInvalidInput(int maxChoice) {
        System.out.printf(Messages.MENU_ERROR_INVALID_INPUT + "%n", maxChoice);
        uiInput.nextLine();
        waitForEnter();
        Display.clear();
    }

    // Custom Menu number of options
    public static int getMenuInput(int maxChoice) {

        while (true) {
            System.out.print("> ");
            try {
                int choice = uiInput.nextInt();
                uiInput.nextLine();

                if (choice >= 1 && choice <= maxChoice) {
                    return choice;
                } else {
                    System.out.printf(Messages.MENU_ERROR_INVALID_INPUT + "%n", maxChoice);
                    UI.waitForEnter();
                    Display.clear();
                }
            } catch (InputMismatchException e) {
                handleMenuInvalidInput(maxChoice);
            } catch (Exception e) {
                handleMenuInvalidInput(maxChoice);
            }
            /*
            Exception Handling ⚠️:
                Invalid input due to non-numeric value
                It is a good practice to have an InputMismatchException
                to handle unexpected input specifically or any [errors/exceptions]
                rather than using a general Exception catch
                Use the general Exception catch as a safety net
                reference: https://www.youtube.com/watch?v=xTtL8E4LzTQ&t=33035s

            It was pain to work with next() and nextLine() to get it working on nextInt():
                Thanks for this tips
                reference: https://youtu.be/B2TfQiFvyYo?si=UjpFgtPfJ9bUApVY
             */
        }
    }

    // With no. of options but with custom Output Method Overloading
    public static int getMenuInput(int maxChoice, String menuDisplay) {

        while (true) {
            Display.printBox(menuDisplay);
            System.out.print("> ");
            try {
                int choice = uiInput.nextInt();
                uiInput.nextLine();

                if (choice >= 1 && choice <= maxChoice) {
                    return choice;
                } else {
                    System.out.printf(Messages.MENU_ERROR_INVALID_INPUT + "%n", maxChoice);
                    UI.waitForEnter();
                    Display.clear();
                }
            } catch (InputMismatchException e) {
                handleMenuInvalidInput(maxChoice);
            } catch (Exception e) {
                handleMenuInvalidInput(maxChoice);
            }
            /*
            Exception Handling ⚠️:
                Invalid input due to non-numeric value
                It is a good practice to have an InputMismatchException
                to handle unexpected input specifically or any [errors/exceptions]
                rather than using a general Exception catch
                Use the general Exception catch as a safety net
                reference: https://www.youtube.com/watch?v=xTtL8E4LzTQ&t=33035s

            It was pain to work with next() and nextLine() to get it working on nextInt():
                Thanks for this tips
                reference: https://youtu.be/B2TfQiFvyYo?si=UjpFgtPfJ9bUApVY
             */
        }
    }

}
