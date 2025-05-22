package SoloLeveling;

import java.util.Scanner;

public class Menu {
    public static void showWelcomeScreen() {
        System.out.println("                    High Score: 999999\n");
        showSoloLevelingLogo();
        System.out.println("\n\n                 Welcome to Solo Leveling\n");
        System.out.println("                 Press Enter to continue...\n");
        
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        clearScreen();
    }

    private static void showSoloLevelingLogo() {
        System.out.println("             _____       _         __                    _  _           ");
        System.out.println("            |   __| ___ | | ___   |  |    ___  _ _  ___ | ||_| ___  ___ ");
        System.out.println("            |__   || . || || . |  |  |__ | -_|| | || -_|| || ||   || . |");
        System.out.println("            |_____||___||_||___|  |_____||___| \\_/ |___||_||_||_|_||_  |");
        System.out.println("                                                                   |___|");
    }


    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix-like systems (Linux/Mac), use ANSI escape codes directly
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback to ANSI escape codes if other methods fail
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void showMainMenu() {
        clearScreen();
        
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
        
        showSoloLevelingLogo();
        System.out.println("               +-------------------------+");
        System.out.println("               |        MAIN MENU        |");
        System.out.println("               +-------------------------+");
        System.out.println("               | 1. Start Game           |");
        System.out.println("               | 2. Instructions         |");
        System.out.println("               | 3. Credits              |");
        System.out.println("               | 4. Exit                 |");
        System.out.println("               +-------------------------+");
        System.out.print  ("               Choose an option: ");
        
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.println("\nStarting game...");
                clearScreen();
                break;
            case 2:
                System.out.println("\nShowing instructions...");
                clearScreen();
                break;
            case 3:
                System.out.println("\nShowing credits...");
                clearScreen();
                break;
            case 4:
                System.out.println("\nExiting...");
                clearScreen();
                System.out.println("\nArise.");
                System.exit(0);
                break;
            default:
                System.out.println("\nInvalid choice. Please try again.");
                showMainMenu();
                break;
        }
    }
}