
import java.util.Scanner;

public class UI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void waitForEnter() {

        scanner.nextLine();
    }

    //Customized menu input
    public static int getMenuInput(int maxChoice) {
        try {
            String input = scanner.nextLine();
            int choice = Integer.parseInt(input);
            if (choice >= 1 && choice <= maxChoice) {
                return choice;
            }
        } catch (NumberFormatException e) {
            // Invalid input due to non-numeric value
            // It is a good practice to have an NumberFormatException
            // to handle unexpected input specifically or any errors/exceptions
            // rather than using a general Exception catch
            // Use the general Exception catch as a safety net
            // reference: https://www.w3schools.com/java/java_try_catch.asp
            System.out.println("Invalid choice. Please enter a number between 1 and " + maxChoice + ".");
            return -1;
        } catch (Exception e) {
            // SAFETY NET for any other unexpected errors
            System.out.println("Invalid choice. Please enter a number between 1 and " + maxChoice + ".");
            return -1;
        }

        return -1; // if the input is not a number between 1 and 6
    }
}
