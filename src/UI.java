
import java.util.InputMismatchException;
import java.util.Scanner;

// ─────────────────────────────────────────────────────────────
// ▼ UI - Makes the program talk to you
// ─────────────────────────────────────────────────────────────
public class UI {

    static Scanner uiInput = new Scanner(System.in);

    // ─────────────────────────────────────────────────────────────
    // ▼ Basic System UI Utilities
    // ─────────────────────────────────────────────────────────────
    // Waits for user to press Enter
    public static void waitForEnter() {
        System.out.println("Press Enter to continue...");
        uiInput.nextLine();
    }

    // ─────────────────────────────────────────────────────────────
    // ▼ Input Error Handlers
    // ─────────────────────────────────────────────────────────────
    // Generic input error with custom message
    public static void handleInputError(String errorMessage) {
        System.out.printf(errorMessage);
        waitForEnter();
        Display.clear();
    }

    // Handles invalid numeric input in menus
    public static void handleMenuInputError(int maxChoice) {
        System.out.printf(Messages.ERROR_INVALID_MENU_CHOICE, maxChoice);
        waitForEnter();
        Display.clear();
    }

    // ─────────────────────────────────────────────────────────────
    // ▼ Menu Input Methods
    // ─────────────────────────────────────────────────────────────

    /*
     * Trivia: This method took longer to write than the rest of the game combined.
     * 
     * The journey of a couple lines begins with a single Scanner bug...
     * - Tried nextInt() but it left newlines
     * - Tried hasNextInt() but it blocked
     * - Created a second Scanner just for one line
     * - Still not sure if it's thread-safe
     * 
     * At this point, I understand why people use game engines.
     * But hey, at least we learned about input streams! 🎮
     */
    // Default: Get numeric input for a menu with a number of options
    public static int getMenuInput(int maxChoice) {

        while (true) {
            System.out.print("> ");
            try {
                String line = uiInput.nextLine().trim();

                if (line.isEmpty()) {
                    handleInputError(Messages.ERROR_EMPTY_MENU_INPUT);
                } else {
                    // Create another scanner just for this line to avoid mixing nextLine() and nextInt()
                    // reference: https://www.geeksforgeeks.org/scanner-nextint-method-in-java-with-examples/
                    Scanner lineScanner = new Scanner(line);
                    int choice = lineScanner.nextInt();
                    lineScanner.close();

                    if (choice >= 1 && choice <= maxChoice) {
                        return choice;
                    } else {
                        handleMenuInputError(maxChoice);
                    }
                }
            } catch (InputMismatchException e) {
                handleInputError(Messages.ERROR_NON_NUMERIC_INPUT);
            } catch (Exception e) {
                handleInputError(Messages.SYSTEM_ERROR_UNKNOWN);
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

    /*
     * Overloaded: Get menu input with additional custom display
     * parameters: maxChoice, menuDisplay
     */
    public static int getMenuInput(int maxChoice, String menuDisplay) {

        while (true) {
            Display.printBox(menuDisplay);
            System.out.print("> ");
            try {
                String line = uiInput.nextLine().trim();

                if (line.isEmpty()) {
                    handleInputError(Messages.ERROR_EMPTY_MENU_INPUT);
                } else {
                    // Create another scanner just for this line to avoid mixing nextLine() and nextInt()
                    // reference: https://www.geeksforgeeks.org/scanner-nextint-method-in-java-with-examples/
                    Scanner lineScanner = new Scanner(line);
                    int choice = lineScanner.nextInt();
                    lineScanner.close();

                    if (choice >= 1 && choice <= maxChoice) {
                        return choice;
                    } else {
                        handleMenuInputError(maxChoice);
                    }
                }
            } catch (InputMismatchException e) {
                handleInputError(Messages.ERROR_NON_NUMERIC_INPUT);
            } catch (Exception e) {
                handleInputError(Messages.SYSTEM_ERROR_UNKNOWN);
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

    /*
     * Overloaded: Get menu input with additional custom display and logo
     * parameters: maxChoice, menuDisplay, logo
     */
    public static int getMenuInput(int maxChoice, String menuDisplay, String logo) {

        while (true) {
            System.out.println(logo);
            Display.printBox(menuDisplay);
            System.out.print("> ");
            try {
                String line = uiInput.nextLine().trim();

                if (line.isEmpty()) {
                    handleInputError(Messages.ERROR_EMPTY_MENU_INPUT);
                } else {
                    // Create another scanner just for this line to avoid mixing nextLine() and nextInt()
                    // reference: https://www.geeksforgeeks.org/scanner-nextint-method-in-java-with-examples/
                    Scanner lineScanner = new Scanner(line);
                    int choice = lineScanner.nextInt();
                    lineScanner.close();

                    if (choice >= 1 && choice <= maxChoice) {
                        return choice;
                    } else {
                        handleMenuInputError(maxChoice);
                    }
                }
            } catch (InputMismatchException e) {
                handleInputError(Messages.ERROR_NON_NUMERIC_INPUT);
            } catch (Exception e) {
                handleInputError(Messages.SYSTEM_ERROR_UNKNOWN);
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



    // ─────────────────────────────────────────────────────────────
    // ▼ String Input
    // ─────────────────────────────────────────────────────────────
    // Reads a non-empty string input from the user
    public static String getStringInput() {
        while (true) {
            System.out.print("> ");
            try {
                String input = uiInput.nextLine().trim();
                if (input.isEmpty()) {
                    handleInputError(Messages.ERROR_EMPTY_MENU_INPUT);
                } else {
                    return input;
                }
            } catch (Exception e) {
                handleInputError(Messages.SYSTEM_ERROR_UNKNOWN);
            }
        }
    }

    public static String getStringInput(String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.print("> ");
            try {
                String input = uiInput.nextLine().trim();
                if (input.isEmpty()) {
                    handleInputError(Messages.ERROR_EMPTY_MENU_INPUT);
                } else {
                    return input;
                }
            } catch (Exception e) {
                handleInputError(Messages.SYSTEM_ERROR_UNKNOWN);
            }
        }
    }

    // ─────────────────────────────────────────────────────────────
    // ▼ Hero Name Validation
    // ─────────────────────────────────────────────────────────────
    // Gets a valid hero name
    public static String getHeroName() {
        while (true) {
            System.out.println("👤 What should we call you, hunter?");
            System.out.print("> ");
            try {
                String input = uiInput.nextLine().trim();
                if (input.isEmpty()) {
                    handleInputError(Messages.ERROR_NAME_EMPTY);
                } else if (hasInvalidCharacters(input)) {
                    handleInputError(Messages.ERROR_NAME_FORMAT);
                } else {
                    Display.clear();
                    System.out.printf("✨ Welcome, %s! The dungeon awaits!%n", input);
                    Display.delay(2);
                    Display.clear();
                    return input;
                }
            } catch (Exception e) {
                handleInputError(Messages.SYSTEM_ERROR_UNKNOWN);
            }
        }
    }

    private static boolean hasInvalidCharacters(String name) {
        String badCharacters = "[]{}@#$%^&*=+|\\~`";

        for (String badChar : badCharacters.split("")) {
            if (name.contains(badChar)) {
                return true;
            }
        }
        return false;
    }
}
