package sololeveling;

import sololeveling.utils.Display;
import java.util.Scanner;

public class Menu {

    public static void showWelcomeScreen() {
        Display.logo();
        System.out.println("                    High Score: 999999\n");
        System.out.println("\n\n                 Welcome to Solo Leveling\n");
        System.out.println("                 Press Enter to continue...\n");
    }

    public static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        int choice = -1;

        while (isRunning) {
            Display.clear();
            Display.logo();
            Display.menu();
            
            // validates input
            try {
                choice = scanner.nextInt();
            } catch (ArithmeticException e) {
                System.out.println("\nInvalid choice. Please try again.");
            } catch (Exception e) {
                // SAFETY NET
                System.out.println("\nInvalid choice. Please try again.");
            }
            
            switch (choice) {
                case 1:
                    System.out.println("\nStarting game...");
                    Display.clear();
                    break;
                case 2:
                    System.out.println("\nShowing instructions...");
                    Display.clear();
                    break;
                case 3:
                    System.out.println("\nShowing credits...");
                    Display.clear();
                    break;
                case 4:
                    Display.clear();
                    System.out.println("\nArise.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }
}