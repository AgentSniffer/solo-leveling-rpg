package sololeveling;

import sololeveling.utils.Display;
import java.util.Scanner;

public class Menu {

    public static void showWelcomeScreen() {
        System.out.println("                    High Score: 999999\n");
        Display.logo();
        System.out.println("\n\n                 Welcome to Solo Leveling\n");
        System.out.println("                 Press Enter to continue...\n");
        
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        Display.clear();
    }

    public static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            for (int i = 0; i < 10; i++) {
                System.out.println();
            }
            
            Display.logo();
            Display.menu();
            
            // validates input
            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("\nInvalid choice. Please try again.");
                showMainMenu();
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
                    showMainMenu();
                    break;
            }
        }
        scanner.close();
    }
}