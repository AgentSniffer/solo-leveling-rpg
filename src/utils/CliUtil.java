package utils;

import cli.LoginCli;
import java.io.IOException;
import java.util.Scanner;

public class CliUtil {

    public static Scanner sc = new Scanner(System.in);

    public static String DEFAULT_COLOR = "\u001B[0m";
    public static String BLACK =  "\u001B[30m";
    public static String RED =    "\u001B[31m";
    public static String GREEN =  "\u001B[32m";
    public static String YELLOW = "\u001B[33m";
    public static String BLUE =   "\u001B[34m";
    public static String PURPLE = "\u001B[35m";
    public static String CYAN =   "\u001B[36m";
    public static String WHITE =  "\u001B[37m";
    public static String RESET =  "\u001B[0m";

    public static void displayMenu(String text) {
        clearScreen();
        printBox(text);
        System.out.print(DEFAULT_COLOR + "> ");
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
        }
    }

    public static void settings() {
        clearScreen();
        printBox("Settings\n1. 🎨 Change input color\n2. 🔙 Back");
        System.out.print(DEFAULT_COLOR + "> ");

        String input = sc.nextLine().trim();
        System.out.print(RESET);

        switch (input) {
            case "1" -> {
                displaySettingsColor();
            }
            case "2" -> {
                LoginCli.display();
            }
            default -> {
                System.out.println(RED + "\nInvalid input" + RESET);
                pause();
                settings();
            }
        }
    }

    public static void displaySettingsColor() {
        clearScreen();
        printBox("Choose a color\n1. Red\n2. Green\n3. Blue\n4. Yellow\n5. Purple\n6. Cyan\n7. Black\n8. White\n9. Back");

        System.out.print(DEFAULT_COLOR + "> ");

        String input = sc.nextLine().trim();
        resetColor();

        switch (input) {
            case "1" -> DEFAULT_COLOR = RED;
            case "2" -> DEFAULT_COLOR = GREEN;
            case "3" -> DEFAULT_COLOR = BLUE;
            case "4" -> DEFAULT_COLOR = YELLOW;
            case "5" -> DEFAULT_COLOR = PURPLE;
            case "6" -> DEFAULT_COLOR = CYAN;
            case "7" -> DEFAULT_COLOR = BLACK;
            case "8" -> DEFAULT_COLOR = WHITE;
            case "9" -> settings();
            default -> {
                System.out.println(RED + "\nInvalid input" + RESET);
                pause();
                displaySettingsColor();
                return;
            }
        }
        System.out.println("\nColor change successfully");
        pause();
        settings();
    }

    public static void resetColor() {
        System.out.print(RESET);
    }

    public static void pause() {
        System.out.println("Press " + RED + "ENTER" + RESET + " to continue...");
        sc.nextLine();
    }

    public static void exit() {
        System.out.println("Arrivederci Hunter!");
        closeScanner();
        System.exit(0);
    }

    public static void closeScanner() {
        sc.close();
    }

    public static void printBox(String text) {
        String[] textLines = text.split("\n");

        int maxLineLength = 0;
        for (String line : textLines) {
            maxLineLength = Math.max(maxLineLength, line.length());
        }

        String horizontalLine = "";
        for (int i = 0; i < maxLineLength + 2; i++) {
            horizontalLine += "═";
        }

        System.out.println("╔" + horizontalLine + "╗");
        for (String line : textLines) {
            int remainingSpace = (maxLineLength - line.length());

            for (int i = 0; i < remainingSpace; i++) {
                line += " ";
            }
            System.out.println("║" + " " + line + " " + "║");
        }
        System.out.println("╚" + horizontalLine + "╝");
    }
}
