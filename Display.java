
import java.io.IOException;

// Simple display class - handles all screen stuff
public class Display {

    public static void logo() {
        System.out.println("             _____       _         __                    _  _           ");
        System.out.println("            |   __| ___ | | ___   |  |    ___  _ _  ___ | ||_| ___  ___ ");
        System.out.println("            |__   || . || || . |  |  |__ | -_|| | || -_|| || ||   || . |");
        System.out.println("            |_____||___||_||___|  |_____||___| \\_/ |___||_||_||_|_||_  |");
        System.out.println("                                                                   |___|");
    }

    public static void clear() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("windows")) {
                // Windows console clear using cmd
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            } else if (os.contains("linux")) {
                // Linux use terminal clear command
                new ProcessBuilder("clear").inheritIO().start().waitFor();

            } else {
                // Other OS types (Mac, BSD, unknown): fallback
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

        } catch (IOException e) {
            // Fallback to ANSI escape codes if other methods fail
            System.out.print("\033[H\033[2J");
            System.out.flush();

        } catch (Exception e) {
            // Fallback to ANSI escape codes if other methods fail
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        /*
        Exception Handling ⚠️:
            InputMismatchException for non-numeric input.  
            General Exception as fallback and act as a Safety Net.
            reference: https://www.youtube.com/watch?v=xTtL8E4LzTQ&t=33035s
         */

    }

    public static void showWelcomeScreen() {
        System.out.println("                    High Score: 999999\n");
        System.out.println("\n\n                 Welcome to Solo Leveling\n");
    }

    public static void delay(int seconds) {
        try {
            Thread.sleep(seconds * 1000);  // conversion milliseconds to seconds
        } catch (InterruptedException e) {
            // nothing to put here
        }
    }

    // Getter for Displaying Main Menu located on Messages class
    public static String getMENU_OPTIONS() {
        return Messages.MENU_OPTIONS;
    }

    // Customize margin size for box
    public static void printBox(String text, int marginLeft, int marginRight, int marginTop, int marginBottom) {

        String[] textLines = text.split("\n");

        // Find the longest line
        int maxLineLength = 0;
        for (String line : textLines) {
            if (line.length() > maxLineLength) {
                maxLineLength = line.length();
            }
        }

        int totalWidth = maxLineLength + marginLeft + marginRight; // 2 means 2 spaces both side

        // Create horizontal line
        String horizontalLine = "";
        for (int i = 0; i < totalWidth; i++) {
            horizontalLine += "═";
        }

        // Print top border top
        System.out.println("╔" + horizontalLine + "╗");

        // Print top vertical padding
        for (int i = 0; i < marginTop; i++) {
            System.out.print("║");
            for (int j = 0; j < totalWidth; j++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }

        // ==============================================================
        // Print text lines with horizontal and vertical padding
        for (String line : textLines) {
            int rightPadding = maxLineLength - line.length();

            System.out.print("║");

            for (int i = 0; i < marginLeft; i++) {
                System.out.print(" ");
            }

            System.out.print(line);

            for (int i = 0; i < rightPadding + marginRight; i++) {
                System.out.print(" ");
            }

            System.out.println("║");
        }
        // ==============================================================

        // Print bottom vertical padding
        for (int i = 0; i < marginBottom; i++) {
            System.out.print("║");
            for (int j = 0; j < totalWidth; j++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }

        // Print bottom border bottom
        System.out.println("╚" + horizontalLine + "╝");
    }

    // Default Method Overloading with default padding
    public static void printBox(String text) {
        printBox(text, 1, 1, 0, 0);
    }
}
